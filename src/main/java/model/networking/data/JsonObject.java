package model.networking.data;

import com.google.gson.Gson;

public interface JsonObject {
    default JsonObject decode(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, getClass());
    }
    default String encode(JsonObject obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
