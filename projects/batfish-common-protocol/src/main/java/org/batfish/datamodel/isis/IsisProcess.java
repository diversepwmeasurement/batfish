package org.batfish.datamodel.isis;

import static com.google.common.base.MoreObjects.firstNonNull;
import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.batfish.datamodel.GeneratedRoute;
import org.batfish.datamodel.IsoAddress;
import org.batfish.datamodel.Vrf;

/** An IS-IS routing process */
public class IsisProcess implements Serializable {

  public static class Builder {

    private String _exportPolicy;

    private Set<GeneratedRoute> _generatedRoutes;

    private IsisLevelSettings _level1;

    private IsisLevelSettings _level2;

    private IsoAddress _netAddress;

    private boolean _overload;

    private Double _referenceBandwidth;

    private Vrf _vrf;

    private Builder() {
      _generatedRoutes = ImmutableSet.of();
    }

    public IsisProcess build() {
      IsisProcess proc =
          new IsisProcess(
              _exportPolicy,
              firstNonNull(_generatedRoutes, ImmutableSet.of()),
              _level1,
              _level2,
              requireNonNull(_netAddress),
              _overload,
              _referenceBandwidth);
      if (_vrf != null) {
        _vrf.setIsisProcess(proc);
      }
      return proc;
    }

    public @Nonnull Builder setExportPolicy(@Nullable String exportPolicy) {
      _exportPolicy = exportPolicy;
      return this;
    }

    public @Nonnull Builder setGeneratedRoutes(@Nonnull Set<GeneratedRoute> generatedRoutes) {
      _generatedRoutes = generatedRoutes;
      return this;
    }

    public @Nonnull Builder setLevel1(@Nullable IsisLevelSettings level1) {
      _level1 = level1;
      return this;
    }

    public @Nonnull Builder setLevel2(@Nullable IsisLevelSettings level2) {
      _level2 = level2;
      return this;
    }

    public @Nonnull Builder setNetAddress(@Nonnull IsoAddress netAddress) {
      _netAddress = netAddress;
      return this;
    }

    public @Nonnull Builder setOverload(boolean overload) {
      _overload = overload;
      return this;
    }

    public @Nonnull Builder setReferenceBandwidth(@Nullable Double referenceBandwidth) {
      _referenceBandwidth = referenceBandwidth;
      return this;
    }

    public @Nonnull Builder setVrf(@Nonnull Vrf vrf) {
      _vrf = vrf;
      return this;
    }
  }

  private static final String PROP_EXPORT_POLICY = "exportPolicy";
  private static final String PROP_GENERATED_ROUTES = "generatedRoutes";
  private static final String PROP_LEVEL1 = "level1";
  private static final String PROP_LEVEL2 = "level2";
  private static final String PROP_NET_ADDRESS = "netAddress";
  private static final String PROP_OVERLOAD = "overload";
  private static final String PROP_REFERENCE_BANDWIDTH = "referenceBandwidth";

  public static @Nonnull Builder builder() {
    return new Builder();
  }

  @JsonCreator
  private static @Nonnull IsisProcess create(
      @JsonProperty(PROP_EXPORT_POLICY) String exportPolicy,
      @JsonProperty(PROP_GENERATED_ROUTES) Set<GeneratedRoute> generatedRoutes,
      @JsonProperty(PROP_LEVEL1) IsisLevelSettings level1,
      @JsonProperty(PROP_LEVEL2) IsisLevelSettings level2,
      @JsonProperty(PROP_NET_ADDRESS) IsoAddress netAddress,
      @JsonProperty(PROP_OVERLOAD) Boolean overload,
      @JsonProperty(PROP_REFERENCE_BANDWIDTH) Double referenceBandwidth) {
    return new IsisProcess(
        exportPolicy,
        firstNonNull(generatedRoutes, ImmutableSet.of()),
        level1,
        level2,
        requireNonNull(netAddress),
        firstNonNull(overload, false),
        referenceBandwidth);
  }

  private final @Nullable String _exportPolicy;

  private final @Nonnull Set<GeneratedRoute> _generatedRoutes;

  private final @Nullable IsisLevelSettings _level1;

  private final @Nullable IsisLevelSettings _level2;

  private final @Nonnull IsoAddress _netAddress;

  private final boolean _overload;

  private final @Nullable Double _referenceBandwidth;

  private IsisProcess(
      @Nullable String exportPolicy,
      @Nonnull Set<GeneratedRoute> generatedRoutes,
      @Nullable IsisLevelSettings level1,
      @Nullable IsisLevelSettings level2,
      @Nonnull IsoAddress netAddress,
      boolean overload,
      @Nullable Double referenceBandwidth) {
    _exportPolicy = exportPolicy;
    _generatedRoutes = generatedRoutes;
    _level1 = level1;
    _level2 = level2;
    _netAddress = netAddress;
    _overload = overload;
    _referenceBandwidth = referenceBandwidth;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof IsisProcess)) {
      return false;
    }
    IsisProcess rhs = (IsisProcess) obj;
    return Objects.equals(_generatedRoutes, rhs._generatedRoutes)
        && Objects.equals(_level1, rhs._level1)
        && Objects.equals(_level2, rhs._level2)
        && Objects.equals(_netAddress, rhs._netAddress)
        && Objects.equals(_overload, rhs._overload)
        && Objects.equals(_referenceBandwidth, rhs._referenceBandwidth);
  }

  @JsonProperty(PROP_EXPORT_POLICY)
  public @Nullable String getExportPolicy() {
    return _exportPolicy;
  }

  /**
   * Generated IPV4 routes for the purpose of export into IS-IS. These routes are not imported into
   * the main RIB.
   */
  @JsonProperty(PROP_GENERATED_ROUTES)
  public @Nonnull Set<GeneratedRoute> getGeneratedRoutes() {
    return _generatedRoutes;
  }

  @JsonProperty(PROP_LEVEL1)
  public @Nullable IsisLevelSettings getLevel1() {
    return _level1;
  }

  @JsonProperty(PROP_LEVEL2)
  public @Nullable IsisLevelSettings getLevel2() {
    return _level2;
  }

  /** The net address is an ISO address representing the IS-IS router ID. */
  @JsonProperty(PROP_NET_ADDRESS)
  public @Nonnull IsoAddress getNetAddress() {
    return _netAddress;
  }

  @JsonProperty(PROP_OVERLOAD)
  public boolean getOverload() {
    return _overload;
  }

  @JsonProperty(PROP_REFERENCE_BANDWIDTH)
  public @Nullable Double getReferenceBandwidth() {
    return _referenceBandwidth;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        _generatedRoutes, _level1, _level2, _netAddress, _overload, _referenceBandwidth);
  }
}
