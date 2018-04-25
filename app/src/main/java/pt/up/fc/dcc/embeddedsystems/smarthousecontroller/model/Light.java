/*
 * Smart House
 * The API for the Smart House IOT project for embedded systems course
 *
 * OpenAPI spec version: 1.0.2
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * Light
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-04-25T18:48:02.476Z")
public class Light {
  @SerializedName("id")
  private Integer id = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("turnon")
  private Boolean turnon = null;

  @SerializedName("threshold")
  private BigDecimal threshold = null;

  @SerializedName("automatic")
  private Boolean automatic = null;

  public Light id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Light description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(required = true, value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Light turnon(Boolean turnon) {
    this.turnon = turnon;
    return this;
  }

   /**
   * Get turnon
   * @return turnon
  **/
  @ApiModelProperty(required = true, value = "")
  public Boolean isTurnon() {
    return turnon;
  }

  public void setTurnon(Boolean turnon) {
    this.turnon = turnon;
  }

  public Light threshold(BigDecimal threshold) {
    this.threshold = threshold;
    return this;
  }

   /**
   * Get threshold
   * @return threshold
  **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getThreshold() {
    return threshold;
  }

  public void setThreshold(BigDecimal threshold) {
    this.threshold = threshold;
  }

  public Light automatic(Boolean automatic) {
    this.automatic = automatic;
    return this;
  }

   /**
   * Get automatic
   * @return automatic
  **/
  @ApiModelProperty(required = true, value = "")
  public Boolean isAutomatic() {
    return automatic;
  }

  public void setAutomatic(Boolean automatic) {
    this.automatic = automatic;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Light light = (Light) o;
    return Objects.equals(this.id, light.id) &&
        Objects.equals(this.description, light.description) &&
        Objects.equals(this.turnon, light.turnon) &&
        Objects.equals(this.threshold, light.threshold) &&
        Objects.equals(this.automatic, light.automatic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, turnon, threshold, automatic);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Light {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    turnon: ").append(toIndentedString(turnon)).append("\n");
    sb.append("    threshold: ").append(toIndentedString(threshold)).append("\n");
    sb.append("    automatic: ").append(toIndentedString(automatic)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

