package persistence;

import model.Course;
import model.StudentProfile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads StudentProfile from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads StudentProfile from file and returns it;
    // throws IOException if an error occurs reading data from file
    public StudentProfile read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudentProfile(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses StudentProfile from JSON object and returns it
    private StudentProfile parseStudentProfile(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        StudentProfile profile = new StudentProfile(name);
        addCourses(profile, jsonObject);
        return profile;
    }

    // MODIFIES: profile
    // EFFECTS: parses courses from JSON object and adds them to StudentProfile
    private void addCourses(StudentProfile profile, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(profile, nextCourse);
        }
    }

    // MODIFIES: profile
    // EFFECTS: parses a single course from JSON object and adds it to StudentProfile
    private void addCourse(StudentProfile profile, JSONObject jsonObject) {
        String code = jsonObject.getString("code");
        int credits = jsonObject.getInt("credits");
        double grade = jsonObject.getDouble("grade");
        
        Course course = new Course(code, credits, grade);
        profile.addCourse(course);
    }
}