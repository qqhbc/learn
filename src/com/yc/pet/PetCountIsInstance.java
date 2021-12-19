package com.yc.pet;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PetCountIsInstance {
    static class PerCounter extends LinkedHashMap<Class<? extends Pet>, Integer> {
        public PerCounter(){
            Map<Class<? extends Pet>, Integer> map = new HashMap<>();
            for (Class<? extends Pet> allType : LiteralPetCreator.getAllTypes()) {
                map.put(allType, 0);
            }
            super.putAll(map);
        }

        public void count(Pet pet){
            for (Map.Entry<Class<? extends Pet>, Integer> entry : entrySet()) {
                if(entry.getKey().isInstance(pet)) {
                    put(entry.getKey(), entry.getValue() + 1);
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            for (Map.Entry<Class<? extends Pet>, Integer> entry : entrySet()) {
                sb.append(entry.getKey().getSimpleName()).append("=").append(entry.getValue()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("}");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        ZoneId.getAvailableZoneIds().forEach(System.out::println);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("US/Pacific-New"));
        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(LocalDateTime.parse("2020-12-13 12:06:55", dateTimeFormatter));
        System.out.println(LocalDateTime.parse("2020-12-13 12:06:55", dateTimeFormatter2));
    }
}
