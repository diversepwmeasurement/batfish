package org.batfish.vendor.check_point_management;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.batfish.datamodel.AclIpSpace;
import org.batfish.datamodel.EmptyIpSpace;
import org.batfish.datamodel.Ip;
import org.batfish.datamodel.IpRange;
import org.batfish.datamodel.IpSpace;
import org.batfish.datamodel.IpWildcard;
import org.batfish.datamodel.UniverseIpSpace;

/** Create an {@link IpSpace} representing the visited {@link AddressSpace}. */
public class AddressSpaceToIpSpace implements AddressSpaceVisitor<IpSpace> {

  @Override
  public IpSpace visitCpmiAnyObject(CpmiAnyObject cpmiAnyObject) {
    return UniverseIpSpace.INSTANCE;
  }

  @Override
  public IpSpace visitAddressRange(AddressRange addressRange) {
    if (addressRange.getIpv4AddressFirst() == null || addressRange.getIpv4AddressLast() == null) {
      return EmptyIpSpace.INSTANCE;
    }
    return IpRange.range(addressRange.getIpv4AddressFirst(), addressRange.getIpv4AddressLast());
  }

  @Override
  public IpSpace visitGatewayOrServer(GatewayOrServer gatewayOrServer) {
    return gatewayOrServer.getIpv4Address().toIpSpace();
  }

  @Override
  public IpSpace visitGroup(Group group) {
    Set<Uid> allMembers = getDescendantObjects(group, new HashSet<>());
    List<IpSpace> memberIpSpaces =
        allMembers.stream()
            .map(_objs::get)
            .filter(AddressSpace.class::isInstance)
            .map(AddressSpace.class::cast)
            .map(member -> member.accept(this))
            .collect(ImmutableList.toImmutableList());
    IpSpace space = AclIpSpace.union(memberIpSpaces);
    return space == null ? EmptyIpSpace.INSTANCE : space;
  }

  /** Returns descendant objects for the specified {@link Group}. */
  private Set<Uid> getDescendantObjects(Group group, Set<Uid> alreadyTraversedMembers) {
    Uid groupUid = group.getUid();
    if (alreadyTraversedMembers.contains(groupUid)) {
      return ImmutableSet.of();
    }
    alreadyTraversedMembers.add(groupUid);

    Set<Uid> descendantObjects = new HashSet<>();
    for (Uid memberUid : group.getMembers()) {
      TypedManagementObject member = _objs.get(memberUid);
      if (member instanceof Group) {
        descendantObjects.addAll(getDescendantObjects((Group) member, alreadyTraversedMembers));
      } else if (member instanceof AddressSpace) {
        descendantObjects.add(memberUid);
      }
    }
    return descendantObjects;
  }

  @Override
  public IpSpace visitHost(Host host) {
    return host.getIpv4Address().toIpSpace();
  }

  @Override
  public IpSpace visitNetwork(Network network) {
    // TODO Network objects also have a "mask-length4" that we don't currently extract.
    //  If network objects always represent valid Prefixes, it may be simpler to extract
    //  that instead of subnet-mask and convert the network to a PrefixIpSpace.
    // In Network, the mask has bits that matter set, but IpWildcard interprets set mask bits as
    // "don't care". Flip mask to convert to IpWildcard.
    long flippedMask = network.getSubnetMask().asLong() ^ Ip.MAX.asLong();
    return IpWildcard.ipWithWildcardMask(network.getSubnet4(), flippedMask).toIpSpace();
  }

  public AddressSpaceToIpSpace(Map<Uid, TypedManagementObject> objs) {
    _objs = objs;
  }

  private final Map<Uid, TypedManagementObject> _objs;
}