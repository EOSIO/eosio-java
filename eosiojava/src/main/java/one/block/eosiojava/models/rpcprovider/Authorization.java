package one.block.eosiojava.models.rpcprovider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * The Authorization of {@link Action}
 */
public class Authorization implements Serializable {

    /**
     * The Actor name.
     */
    @SerializedName("actor")
    @NotNull
    private String actor;

    /**
     * The Permission of the actor.
     */
    @SerializedName("permission")
    @NotNull
    private String permission;

    /**
     * Instantiates a new Authorization.
     *
     * @param actor the actor name.
     * @param permission the permission of the actor.
     */
    public Authorization(@NotNull String actor, @NotNull String permission) {
        this.actor = actor;
        this.permission = permission;
    }

    /**
     * Gets actor name.
     *
     * @return the actor name.
     */
    @NotNull
    public String getActor() {
        return actor;
    }

    /**
     * Sets actor name.
     *
     * @param actor the actor name.
     */
    public void setActor(@NotNull String actor) {
        this.actor = actor;
    }

    /**
     * Gets permission of the actor.
     *
     * @return the permission of the actor.
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Sets permission of the actor.
     *
     * @param permission the permission of the actor.
     */
    public void setPermission(@NotNull String permission) {
        this.permission = permission;
    }
}
