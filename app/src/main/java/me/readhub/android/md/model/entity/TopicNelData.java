package me.readhub.android.md.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopicNelData {

    private boolean state;

    @SerializedName("result")
    private List<Result> resultList;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public static class Result {

        private String nerName;

        private String entityId;

        private String entityName;

        private String entityType;

        private String entityUniqueId;

        public String getNerName() {
            return nerName;
        }

        public void setNerName(String nerName) {
            this.nerName = nerName;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }

        public String getEntityUniqueId() {
            return entityUniqueId;
        }

        public void setEntityUniqueId(String entityUniqueId) {
            this.entityUniqueId = entityUniqueId;
        }
        
    }

}
