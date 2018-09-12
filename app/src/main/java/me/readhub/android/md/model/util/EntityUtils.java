package me.readhub.android.md.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.DateTimeParseException;

import java.io.IOException;

public final class EntityUtils {

    private EntityUtils() {}

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeAdapter())
            .create();

    private static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private static final DateTimeFormatter formatterCompat = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .toFormatter();

        @Override
        public void write(JsonWriter out, OffsetDateTime offsetDateTime) throws IOException {
            if (offsetDateTime == null) {
                out.nullValue();
            } else {
                out.value(offsetDateTime.toString());
            }
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            } else {
                String value = in.nextString();
                // TODO 有些时间结构不符合标准，这里做兼容
                try {
                    return OffsetDateTime.parse(value);
                } catch (DateTimeParseException e) {
                    return OffsetDateTime.of(LocalDateTime.parse(value, formatterCompat), ZoneOffset.UTC);
                }
            }
        }

    }

}
