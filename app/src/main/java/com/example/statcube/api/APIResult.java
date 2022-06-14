package com.example.statcube.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIResult {
    private boolean Status;
    private int Code;

    private String MessageType;
    private String Message;
    private JSONObject ObjectMessages;
    private JSONArray ArrayMessages;

    private boolean HasResult;
    private String ResultType;
    private JSONObject ObjectResult;
    private JSONArray ArrayResult;

    public APIResult(JSONObject result) {
        try {
            Status = result.getBoolean("status");
            Code = result.getInt("code");
            Object tempMessage = result.get("message");

            if (tempMessage instanceof String) {
                MessageType = "string";
                Message = (String) tempMessage;
            } else if (tempMessage instanceof JSONArray) {
                MessageType = "array";
                ArrayMessages = (JSONArray) tempMessage;
            } else {
                MessageType = "object";
                ObjectMessages = (JSONObject) tempMessage;
            }

            HasResult = result.has("result");
            if (HasResult) {
                Object tempResult = result.get("result");
                if (tempMessage instanceof JSONArray) {
                    ResultType = "array";
                    ArrayResult = (JSONArray) tempResult;
                } else {
                    ResultType = "object";
                    ObjectResult = (JSONObject) tempResult;
                }
            }
        } catch (JSONException e) {
            Log.e("API Error", "Parse JSON Error");
        }
    }

    public boolean getStatus() {
        return Status;
    }

    public int getCode() {
        return Code;
    }

    public String getMessageType() {
        return MessageType;
    }

    public Object getMessage() {
        if (MessageType.equals("string")) {
            return Message;
        } else if (MessageType.equals("array")) {
            return ArrayMessages;
        } else {
            return ObjectMessages;
        }
    }

    public String getResultType() {
        return ResultType;
    }

    public Object getResult() {
        if (!HasResult) {
            return null;
        }

        if (ResultType.equals("array")) {
            return ArrayResult;
        } else {
            return ObjectResult;
        }
    }
}
