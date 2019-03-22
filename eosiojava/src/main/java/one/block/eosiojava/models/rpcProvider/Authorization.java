package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * The Authorization of Action
 */
public class Authorization implements Serializable {

    /**
     * The Actor.
     */
    @SerializedName("actor")
    String actor;

    /**
     * The Permission.
     */
    @SerializedName("permission")
    String permission;

    /**
     * Instantiates a new Authorization.
     *
     * @param actor the actor
     * @param permission the permission
     */
    public Authorization(String actor, String permission) {
        this.actor = actor;
        this.permission = permission;
    }

    /**
     * Gets actor.
     *
     * @return the actor
     */
    public String getActor() {
        return actor;
    }

    /**
     * Sets actor.
     *
     * @param actor the actor
     */
    public void setActor(String actor) {
        this.actor = actor;
    }
}
