import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class EpicAdapter extends TypeAdapter<Epic> {

    @Override
    public void write(JsonWriter jsonWriter, Epic epic) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id").value(epic.getId());
        jsonWriter.name("type").value(epic.getType().toString());
        jsonWriter.name("name").value(epic.getName());
        jsonWriter.name("status").value(epic.getStatus().toString());
        jsonWriter.name("description").value(epic.getDescription());
        jsonWriter.name("startTime").value(epic.getStartTime().toString());
        jsonWriter.name("duration").value(epic.getDuration().toString());
        jsonWriter.endObject();
    }

    @Override
    public Epic read(JsonReader jsonReader) throws IOException {
        int id = 0;
        String name = null;
        String description = null;
        String duration = null;
        String startTime = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String fieldName = jsonReader.nextName();
            switch (fieldName) {
                case "id":
                    id = jsonReader.nextInt();
                    break;
                case "name":
                    name = jsonReader.nextString();
                    break;
                case "description":
                    description = jsonReader.nextString();
                    break;
                case "duration":
                    duration = jsonReader.nextString();
                    break;
                case "startTime":
                    startTime = jsonReader.nextString();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();

        if (id != 0) {
            return new Epic(name, description, id, startTime, duration);
        }
        return new Epic(name, description, startTime, duration);
    }
}