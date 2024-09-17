package dk.obhnothing.utilities;

import java.lang.reflect.Field;

/**
 *
 *
 *
 *
 *
 *
 */
public class JsonUtils
{

    public static String fromClass(Object o)
    {
        String jsonBourne = "{";
        Field[] fields = o.getClass().getFields();
        try {
            for (Field field : fields) {
                jsonBourne += String.format("%n%s : %s,", field.getName(), field.get(o));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonBourne + String.format("%n}");
    }

}














