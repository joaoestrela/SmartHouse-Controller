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
 * SensorData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-04-25T18:48:02.476Z")
public class SensorData {
  @SerializedName("value")
  private BigDecimal value = null;

  @SerializedName("unit")
  private String unit = null;

  public SensorData value(BigDecimal value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public SensorData unit(String unit) {
    this.unit = unit;
    return this;
  }

   /**
   * Get unit
   * @return unit
  **/
  @ApiModelProperty(required = true, value = "")
  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SensorData sensorData = (SensorData) o;
    return Objects.equals(this.value, sensorData.value) &&
        Objects.equals(this.unit, sensorData.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, unit);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SensorData {\n");
    
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
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

