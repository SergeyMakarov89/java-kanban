import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class TaskAdapter extends TypeAdapter<Task> {

    @Override
    public void write(JsonWriter jsonWriter, Task task) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id").value(task.getId());
        jsonWriter.name("type").value(task.getType().toString());
        jsonWriter.name("name").value(task.getName());
        jsonWriter.name("status").value(task.getStatus().toString());
        jsonWriter.name("description").value(task.getDescription());
        jsonWriter.name("startTime").value(task.getStartTime().toString());
        jsonWriter.name("duration").value(task.getDuration().toString());
        jsonWriter.endObject();
    }

    @Override
    public Task read(JsonReader jsonReader) throws IOException {
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
            return new Task(name, description, id, startTime, duration);
        }
        return new Task(name, description, startTime, duration);
    }
}