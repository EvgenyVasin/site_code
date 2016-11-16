package ru.jskills.vo;

/**
 * Created by safin.v on 17.10.2016.
 */
public class MsgCode {
    public final static int CODE_MSG=0;
    public final static int CMD_MSG=1;
    public final static int OK_MSG=2;

    private String sessionId;
    private int commandId;
    private String msg;

    public String getSessionId() {
        return sessionId;
    }
    public int getCommandId() {
        return commandId;
    }

    public String getMsg() {
        return msg;
    }



    public MsgCode(String sessionId, int commandId, String msg) {
        this.sessionId = sessionId;
        this. commandId = commandId;
        this.msg = msg;
    }

    public MsgCode() {
    }
}
