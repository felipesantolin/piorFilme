package com.gra.worstmovies.util;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        } else if (obj == null){
            return false;
        }  else if(!(obj instanceof AbstractEntity)){
            return false;
        } else {
            AbstractEntity other = (AbstractEntity) obj;
            Long myId = (Long)this.getId();
            Long otherId = (Long) other.getId();
            if(otherId != null && myId != null){
                return myId == otherId;
            }
            return false;
        }
    }

    public abstract Long getId();
}
