package org.batfish.question.definedstructures;

import static com.google.common.base.MoreObjects.firstNonNull;

import com.google.common.collect.ImmutableSortedSet;
import java.util.Comparator;
import java.util.Objects;
import java.util.SortedSet;
import javax.annotation.Nonnull;

public class DefinedStructureRow implements Comparable<DefinedStructureRow> {

  private final @Nonnull SortedSet<Integer> _definitionLines;

  private final @Nonnull String _filename;

  private final @Nonnull String _structType;

  private final @Nonnull String _structName;

  public DefinedStructureRow(
      String filename, String structType, String structName, SortedSet<Integer> definitionLines) {
    if (filename == null) {
      throw new IllegalArgumentException(
          "Cannot initialize DefinedStructureInfo: filename is null");
    }
    if (structType == null) {
      throw new IllegalArgumentException(
          "Cannot initialize DefinedStructureInfo: structType is null");
    }
    if (structName == null) {
      throw new IllegalArgumentException(
          "Cannot initialize DefinedStructureInfo: structName is null");
    }
    _filename = filename;
    _structType = structType;
    _structName = structName;
    _definitionLines = firstNonNull(definitionLines, ImmutableSortedSet.of());
  }

  public @Nonnull SortedSet<Integer> getDefinitionLines() {
    return _definitionLines;
  }

  public @Nonnull String getFilename() {
    return _filename;
  }

  public @Nonnull String getStructName() {
    return _structName;
  }

  public @Nonnull String getStructType() {
    return _structType;
  }

  @Override
  public int compareTo(@Nonnull DefinedStructureRow o) {
    return Comparator.comparing(DefinedStructureRow::getFilename)
        .thenComparing(DefinedStructureRow::getStructType)
        .thenComparing(DefinedStructureRow::getStructName)
        .compare(this, o);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DefinedStructureRow)) {
      return false;
    }
    DefinedStructureRow other = (DefinedStructureRow) o;
    return Objects.equals(_definitionLines, other._definitionLines)
        && Objects.equals(_filename, other._filename)
        && Objects.equals(_structName, other._structName)
        && Objects.equals(_structType, other._structType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_definitionLines, _filename, _structName, _structType);
  }

  @Override
  public String toString() {
    return String.format(
        "file=%s structType=%s structName=%s defLines=%s",
        _filename, _structType, _structType, _definitionLines);
  }
}
