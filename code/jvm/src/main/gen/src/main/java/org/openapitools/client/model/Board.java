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
import org.openapitools.client.model.Cell;

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
 * Board
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-12-10T22:14:03.953364700Z[Europe/Lisbon]")
public class Board {
  public static final String SERIALIZED_NAME_POSITIONS = "positions";
  @SerializedName(SERIALIZED_NAME_POSITIONS)
  private Cell positions;

  public static final String SERIALIZED_NAME_BOARD_SIZE = "boardSize";
  @SerializedName(SERIALIZED_NAME_BOARD_SIZE)
  private Integer boardSize;

  public Board() {
  }

  public Board positions(Cell positions) {
    
    this.positions = positions;
    return this;
  }

   /**
   * Get positions
   * @return positions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Cell getPositions() {
    return positions;
  }


  public void setPositions(Cell positions) {
    this.positions = positions;
  }


  public Board boardSize(Integer boardSize) {
    
    this.boardSize = boardSize;
    return this;
  }

   /**
   * Get boardSize
   * @return boardSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getBoardSize() {
    return boardSize;
  }


  public void setBoardSize(Integer boardSize) {
    this.boardSize = boardSize;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Board board = (Board) o;
    return Objects.equals(this.positions, board.positions) &&
        Objects.equals(this.boardSize, board.boardSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(positions, boardSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Board {\n");
    sb.append("    positions: ").append(toIndentedString(positions)).append("\n");
    sb.append("    boardSize: ").append(toIndentedString(boardSize)).append("\n");
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
    openapiFields.add("positions");
    openapiFields.add("boardSize");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to Board
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (Board.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in Board is not found in the empty JSON string", Board.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Board.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Board` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      // validate the optional field `positions`
      if (jsonObj.get("positions") != null && !jsonObj.get("positions").isJsonNull()) {
        Cell.validateJsonObject(jsonObj.getAsJsonObject("positions"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Board.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Board' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Board> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Board.class));

       return (TypeAdapter<T>) new TypeAdapter<Board>() {
           @Override
           public void write(JsonWriter out, Board value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Board read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Board given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Board
  * @throws IOException if the JSON string is invalid with respect to Board
  */
  public static Board fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Board.class);
  }

 /**
  * Convert an instance of Board to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

