package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * The Authorization of Action
 */
public class Authorization implements Serializable {

    /**
     * The Actor.
     */
    @SerializedName("actor")
    @NotNull
    String actor;

    /**
     * The Permission.
     */
    @SerializedName("permission")
    @NotNull
    String permission;

    /**
     * Instantiates a new Authorization.
     *
     * @param actor the actor
     * @param permission the permission
     */
    public Authorization(@NotNull String actor, @NotNull String permission) {
        this.actor = actor;
        this.permission = permission;
    }

    /**
     * Gets actor.
     *
     * @return the actor
     */
    @NotNull
    public String getActor() {
        return actor;
    }

    /**
     * Sets actor.
     *
     * @param actor the actor
     */
    public void setActor(@NotNull String actor) {
        this.actor = actor;
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Sets permission.
     *
     * @param permission the permission
     */
    public void setPermission(@NotNull String permission) {
        this.permission = permission;
    }
}
