package com.yc.design;

import java.util.*;

public class CycleDepend {
    private final Map<String, String> aliasMap = new HashMap<>(16);

    public void add(String name, List<String> aliases) {
        for (String alias : aliases) {
            cycleCheck(name, alias);
            aliasMap.put(alias, name);
        }
    }

    public void cycleCheck(String name, String alias) {
        if (hasAlias(alias, name)) {
            throw new IllegalArgumentException("exist cycle depend");
        }
    }

    public boolean hasAlias(String name, String alias) {
        String registerName = aliasMap.get(alias);
        return Objects.equals(name, registerName) ||
                (Objects.nonNull(registerName) && hasAlias(name, registerName));
    }

    public static void main(String[] args) {
        CycleDepend cycleDepend = new CycleDepend();
        cycleDepend.add("B", Collections.singletonList("A"));
        cycleDepend.add("C", Collections.singletonList("B"));
        cycleDepend.add("A", Collections.singletonList("C"));
    }
}
