import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class SubTaskAdapter extends TypeAdapter<SubTask> {

    @Override
    public void write(JsonWriter jsonWriter, SubTask subTask) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id").value(subTask.getId());
        jsonWriter.name("type").value(subTask.getType().toString());
        jsonWriter.name("name").value(subTask.getName());
        jsonWriter.name("status").value(subTask.getStatus().toString());
        jsonWriter.name("parrentId").value(subTask.getParrentId());
        jsonWriter.name("description").value(subTask.getDescription());
        jsonWriter.name("startTime").value(subTask.getStartTime().toString());
        jsonWriter.name("duration").value(subTask.getDuration().toString());
        jsonWriter.endObject();
    }

    @Override
    public SubTask read(JsonReader jsonReader) throws IOException {
        int id = 0;
        int parrentId = 0;
        String name = null;
        String description = null;
        String duration = null;
        String startTime = null;
        String status = null;

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
                case "parrentId":
                    parrentId = jsonReader.nextInt();
                    break;
                case "duration":
                    duration = jsonReader.nextString();
                    break;
                case "startTime":
                    startTime = jsonReader.nextString();
                    break;
                case "status":
                    status = jsonReader.nextString();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();

        if (id != 0) {
            return new SubTask(name, description, id, parrentId, startTime, duration, status);
        }
        return new SubTask(name, description, parrentId, startTime,  duration);
    }
}