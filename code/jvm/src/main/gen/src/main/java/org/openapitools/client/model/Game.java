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
import java.util.UUID;
import org.openapitools.client.model.Board;
import org.openapitools.client.model.Rules;
import org.openapitools.client.model.User;

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
 * Game
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-12-10T22:14:03.953364700Z[Europe/Lisbon]")
public class Game {
  public static final String SERIALIZED_NAME_GAME_ID = "gameId";
  @SerializedName(SERIALIZED_NAME_GAME_ID)
  private UUID gameId;

  public static final String SERIALIZED_NAME_USERS = "users";
  @SerializedName(SERIALIZED_NAME_USERS)
  private User users;

  public static final String SERIALIZED_NAME_BOARD = "board";
  @SerializedName(SERIALIZED_NAME_BOARD)
  private Board board;

  public static final String SERIALIZED_NAME_CURRENT_PLAYER = "currentPlayer";
  @SerializedName(SERIALIZED_NAME_CURRENT_PLAYER)
  private User currentPlayer;

  public static final String SERIALIZED_NAME_SCORE = "score";
  @SerializedName(SERIALIZED_NAME_SCORE)
  private Integer score;

  public static final String SERIALIZED_NAME_NOW = "now";
  @SerializedName(SERIALIZED_NAME_NOW)
  private Object now;

  public static final String SERIALIZED_NAME_RULES = "rules";
  @SerializedName(SERIALIZED_NAME_RULES)
  private Rules rules;

  public Game() {
  }

  public Game gameId(UUID gameId) {
    
    this.gameId = gameId;
    return this;
  }

   /**
   * Get gameId
   * @return gameId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public UUID getGameId() {
    return gameId;
  }


  public void setGameId(UUID gameId) {
    this.gameId = gameId;
  }


  public Game users(User users) {
    
    this.users = users;
    return this;
  }

   /**
   * Get users
   * @return users
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public User getUsers() {
    return users;
  }


  public void setUsers(User users) {
    this.users = users;
  }


  public Game board(Board board) {
    
    this.board = board;
    return this;
  }

   /**
   * Get board
   * @return board
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Board getBoard() {
    return board;
  }


  public void setBoard(Board board) {
    this.board = board;
  }


  public Game currentPlayer(User currentPlayer) {
    
    this.currentPlayer = currentPlayer;
    return this;
  }

   /**
   * Get currentPlayer
   * @return currentPlayer
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public User getCurrentPlayer() {
    return currentPlayer;
  }


  public void setCurrentPlayer(User currentPlayer) {
    this.currentPlayer = currentPlayer;
  }


  public Game score(Integer score) {
    
    this.score = score;
    return this;
  }

   /**
   * Get score
   * @return score
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getScore() {
    return score;
  }


  public void setScore(Integer score) {
    this.score = score;
  }


  public Game now(Object now) {
    
    this.now = now;
    return this;
  }

   /**
   * Get now
   * @return now
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Object getNow() {
    return now;
  }


  public void setNow(Object now) {
    this.now = now;
  }


  public Game rules(Rules rules) {
    
    this.rules = rules;
    return this;
  }

   /**
   * Get rules
   * @return rules
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Rules getRules() {
    return rules;
  }


  public void setRules(Rules rules) {
    this.rules = rules;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Game game = (Game) o;
    return Objects.equals(this.gameId, game.gameId) &&
        Objects.equals(this.users, game.users) &&
        Objects.equals(this.board, game.board) &&
        Objects.equals(this.currentPlayer, game.currentPlayer) &&
        Objects.equals(this.score, game.score) &&
        Objects.equals(this.now, game.now) &&
        Objects.equals(this.rules, game.rules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gameId, users, board, currentPlayer, score, now, rules);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Game {\n");
    sb.append("    gameId: ").append(toIndentedString(gameId)).append("\n");
    sb.append("    users: ").append(toIndentedString(users)).append("\n");
    sb.append("    board: ").append(toIndentedString(board)).append("\n");
    sb.append("    currentPlayer: ").append(toIndentedString(currentPlayer)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
    sb.append("    now: ").append(toIndentedString(now)).append("\n");
    sb.append("    rules: ").append(toIndentedString(rules)).append("\n");
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
    openapiFields.add("gameId");
    openapiFields.add("users");
    openapiFields.add("board");
    openapiFields.add("currentPlayer");
    openapiFields.add("score");
    openapiFields.add("now");
    openapiFields.add("rules");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to Game
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (Game.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in Game is not found in the empty JSON string", Game.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Game.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Game` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if ((jsonObj.get("gameId") != null && !jsonObj.get("gameId").isJsonNull()) && !jsonObj.get("gameId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `gameId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("gameId").toString()));
      }
      // validate the optional field `users`
      if (jsonObj.get("users") != null && !jsonObj.get("users").isJsonNull()) {
        User.validateJsonObject(jsonObj.getAsJsonObject("users"));
      }
      // validate the optional field `board`
      if (jsonObj.get("board") != null && !jsonObj.get("board").isJsonNull()) {
        Board.validateJsonObject(jsonObj.getAsJsonObject("board"));
      }
      // validate the optional field `currentPlayer`
      if (jsonObj.get("currentPlayer") != null && !jsonObj.get("currentPlayer").isJsonNull()) {
        User.validateJsonObject(jsonObj.getAsJsonObject("currentPlayer"));
      }
      // validate the optional field `rules`
      if (jsonObj.get("rules") != null && !jsonObj.get("rules").isJsonNull()) {
        Rules.validateJsonObject(jsonObj.getAsJsonObject("rules"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Game.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Game' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Game> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Game.class));

       return (TypeAdapter<T>) new TypeAdapter<Game>() {
           @Override
           public void write(JsonWriter out, Game value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Game read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Game given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Game
  * @throws IOException if the JSON string is invalid with respect to Game
  */
  public static Game fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Game.class);
  }

 /**
  * Convert an instance of Game to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

