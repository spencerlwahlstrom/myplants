package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the PlantData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "PlantData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class PlantData implements Model {
  public static final QueryField ID = field("PlantData", "id");
  public static final QueryField NAME = field("PlantData", "name");
  public static final QueryField DESCRIPTION = field("PlantData", "description");
  public static final QueryField IMAGE = field("PlantData", "image");
  public static final QueryField LAST_WATERED = field("PlantData", "lastWatered");
  public static final QueryField LAST_FERTILIZED = field("PlantData", "lastFertilized");
  public static final QueryField WATER_INTERVAL = field("PlantData", "waterInterval");
  public static final QueryField FERTILIZE_INTERVAL = field("PlantData", "fertilizeInterval");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="String") String lastWatered;
  private final @ModelField(targetType="String") String lastFertilized;
  private final @ModelField(targetType="Int") Integer waterInterval;
  private final @ModelField(targetType="Int") Integer fertilizeInterval;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getImage() {
      return image;
  }
  
  public String getLastWatered() {
      return lastWatered;
  }
  
  public String getLastFertilized() {
      return lastFertilized;
  }
  
  public Integer getWaterInterval() {
      return waterInterval;
  }
  
  public Integer getFertilizeInterval() {
      return fertilizeInterval;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private PlantData(String id, String name, String description, String image, String lastWatered, String lastFertilized, Integer waterInterval, Integer fertilizeInterval) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.image = image;
    this.lastWatered = lastWatered;
    this.lastFertilized = lastFertilized;
    this.waterInterval = waterInterval;
    this.fertilizeInterval = fertilizeInterval;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      PlantData plantData = (PlantData) obj;
      return ObjectsCompat.equals(getId(), plantData.getId()) &&
              ObjectsCompat.equals(getName(), plantData.getName()) &&
              ObjectsCompat.equals(getDescription(), plantData.getDescription()) &&
              ObjectsCompat.equals(getImage(), plantData.getImage()) &&
              ObjectsCompat.equals(getLastWatered(), plantData.getLastWatered()) &&
              ObjectsCompat.equals(getLastFertilized(), plantData.getLastFertilized()) &&
              ObjectsCompat.equals(getWaterInterval(), plantData.getWaterInterval()) &&
              ObjectsCompat.equals(getFertilizeInterval(), plantData.getFertilizeInterval()) &&
              ObjectsCompat.equals(getCreatedAt(), plantData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), plantData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getImage())
      .append(getLastWatered())
      .append(getLastFertilized())
      .append(getWaterInterval())
      .append(getFertilizeInterval())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("PlantData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("lastWatered=" + String.valueOf(getLastWatered()) + ", ")
      .append("lastFertilized=" + String.valueOf(getLastFertilized()) + ", ")
      .append("waterInterval=" + String.valueOf(getWaterInterval()) + ", ")
      .append("fertilizeInterval=" + String.valueOf(getFertilizeInterval()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static PlantData justId(String id) {
    return new PlantData(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      image,
      lastWatered,
      lastFertilized,
      waterInterval,
      fertilizeInterval);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    PlantData build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep image(String image);
    BuildStep lastWatered(String lastWatered);
    BuildStep lastFertilized(String lastFertilized);
    BuildStep waterInterval(Integer waterInterval);
    BuildStep fertilizeInterval(Integer fertilizeInterval);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private String image;
    private String lastWatered;
    private String lastFertilized;
    private Integer waterInterval;
    private Integer fertilizeInterval;
    @Override
     public PlantData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new PlantData(
          id,
          name,
          description,
          image,
          lastWatered,
          lastFertilized,
          waterInterval,
          fertilizeInterval);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
        return this;
    }
    
    @Override
     public BuildStep lastWatered(String lastWatered) {
        this.lastWatered = lastWatered;
        return this;
    }
    
    @Override
     public BuildStep lastFertilized(String lastFertilized) {
        this.lastFertilized = lastFertilized;
        return this;
    }
    
    @Override
     public BuildStep waterInterval(Integer waterInterval) {
        this.waterInterval = waterInterval;
        return this;
    }
    
    @Override
     public BuildStep fertilizeInterval(Integer fertilizeInterval) {
        this.fertilizeInterval = fertilizeInterval;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String description, String image, String lastWatered, String lastFertilized, Integer waterInterval, Integer fertilizeInterval) {
      super.id(id);
      super.name(name)
        .description(description)
        .image(image)
        .lastWatered(lastWatered)
        .lastFertilized(lastFertilized)
        .waterInterval(waterInterval)
        .fertilizeInterval(fertilizeInterval);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
    
    @Override
     public CopyOfBuilder lastWatered(String lastWatered) {
      return (CopyOfBuilder) super.lastWatered(lastWatered);
    }
    
    @Override
     public CopyOfBuilder lastFertilized(String lastFertilized) {
      return (CopyOfBuilder) super.lastFertilized(lastFertilized);
    }
    
    @Override
     public CopyOfBuilder waterInterval(Integer waterInterval) {
      return (CopyOfBuilder) super.waterInterval(waterInterval);
    }
    
    @Override
     public CopyOfBuilder fertilizeInterval(Integer fertilizeInterval) {
      return (CopyOfBuilder) super.fertilizeInterval(fertilizeInterval);
    }
  }
  
}
