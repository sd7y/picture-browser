package net.aplat.pb.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Labelable {

    List<Label> getLabels();

    default void removeLabel(Label... labels) {
        removeLabel(Arrays.stream(labels).map(Label::getName).collect(Collectors.toList()));
    }
    default void removeLabel(String... labels) {
        removeLabel(Arrays.asList(labels));
    }
    default void removeLabel(List<String> labels) {
        for (String label : labels) {
            for (int i = getLabels().size() - 1; i >= 0; i--) {
                if (label.equals(getLabels().get(i).getName())) {
                    getLabels().remove(i);
                }
            }
        }
    }

    default boolean anyLabelMatch(String... labels) {
        return anyLabelMatch(Arrays.asList(labels));
    }

    default boolean anyLabelMatch(List<String> labels) {
        return getLabels().stream().anyMatch(l -> labels.contains(l.getName()));
    }
}
