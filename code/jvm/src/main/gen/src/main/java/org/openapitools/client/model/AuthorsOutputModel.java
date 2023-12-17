/*
 * 2023_daw_leic53d_2023_daw_leic53d_g09 API
 * 2023_daw_leic53d_2023_daw_leic53d_g09 API
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.Author;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * AuthorsOutputModel
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-12-10T22:14:03.953364700Z[Europe/Lisbon]")
public class AuthorsOutputModel {
  public static final String SERIALIZED_NAME_AUTHORS = "authors";
  @SerializedName(SERIALIZED_NAME_AUTHORS)
  private List<Author> authors = null;

  public AuthorsOutputModel() {
  }

  public AuthorsOutputModel authors(List<Author> authors) {
    
    this.authors = authors;
    return this;
  }

  public AuthorsOutputModel addAuthorsItem(Author authorsItem) {
    if (this.authors == null) {
      this.authors = new ArrayList<>();
    }
    this.authors.add(authorsItem);
    return this;
  }

   /**
   * Get authors
   * @return authors
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Author> getAuthors() {
    return authors;
  }


  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthorsOutputModel authorsOutputModel = (AuthorsOutputModel) o;
    return Objects.equals(this.authors, authorsOutputModel.authors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthorsOutputModel {\n");
    sb.append("    authors: ").append(toIndentedString(authors)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("authors");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to AuthorsOutputModel
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (AuthorsOutputModel.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in AuthorsOutputModel is not found in the empty JSON string", AuthorsOutputModel.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!AuthorsOutputModel.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AuthorsOutputModel` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if (jsonObj.get("authors") != null && !jsonObj.get("authors").isJsonNull()) {
        JsonArray jsonArrayauthors = jsonObj.getAsJsonArray("authors");
        if (jsonArrayauthors != null) {
          // ensure the json data is an array
          if (!jsonObj.get("authors").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `authors` to be an array in the JSON string but got `%s`", jsonObj.get("authors").toString()));
          }

          // validate the optional field `authors` (array)
          for (int i = 0; i < jsonArrayauthors.size(); i++) {
            Author.validateJsonObject(jsonArrayauthors.get(i).getAsJsonObject());
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AuthorsOutputModel.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AuthorsOutputModel' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AuthorsOutputModel> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AuthorsOutputModel.class));

       return (TypeAdapter<T>) new TypeAdapter<AuthorsOutputModel>() {
           @Override
           public void write(JsonWriter out, AuthorsOutputModel value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AuthorsOutputModel read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AuthorsOutputModel given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AuthorsOutputModel
  * @throws IOException if the JSON string is invalid with respect to AuthorsOutputModel
  */
  public static AuthorsOutputModel fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AuthorsOutputModel.class);
  }

 /**
  * Convert an instance of AuthorsOutputModel to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
