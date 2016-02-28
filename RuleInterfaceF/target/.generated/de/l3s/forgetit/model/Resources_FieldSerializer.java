package de.l3s.forgetit.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class Resources_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getAuthor(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::author;
  }-*/;
  
  private static native void setAuthor(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::author = value;
  }-*/;
  
  private static native double getCorrectMB(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::correctMB;
  }-*/;
  
  private static native void setCorrectMB(de.l3s.forgetit.model.Resources instance, double value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::correctMB = value;
  }-*/;
  
  private static native double getCorrectPV(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::correctPV;
  }-*/;
  
  private static native void setCorrectPV(de.l3s.forgetit.model.Resources instance, double value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::correctPV = value;
  }-*/;
  
  private static native double getGenMB(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::genMB;
  }-*/;
  
  private static native void setGenMB(de.l3s.forgetit.model.Resources instance, double value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::genMB = value;
  }-*/;
  
  private static native java.lang.String getGenPV(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::genPV;
  }-*/;
  
  private static native void setGenPV(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::genPV = value;
  }-*/;
  
  private static native int getId(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::id;
  }-*/;
  
  private static native void setId(de.l3s.forgetit.model.Resources instance, int value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::id = value;
  }-*/;
  
  private static native java.lang.String getName(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::name;
  }-*/;
  
  private static native void setName(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::name = value;
  }-*/;
  
  private static native java.lang.String getPublishedDate(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::publishedDate;
  }-*/;
  
  private static native void setPublishedDate(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::publishedDate = value;
  }-*/;
  
  private static native java.lang.String getRuleSet(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::ruleSet;
  }-*/;
  
  private static native void setRuleSet(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::ruleSet = value;
  }-*/;
  
  private static native java.lang.String getScenario(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::scenario;
  }-*/;
  
  private static native void setScenario(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::scenario = value;
  }-*/;
  
  private static native java.lang.String getSemanticType(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::semanticType;
  }-*/;
  
  private static native void setSemanticType(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::semanticType = value;
  }-*/;
  
  private static native boolean getStatus(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::status;
  }-*/;
  
  private static native void setStatus(de.l3s.forgetit.model.Resources instance, boolean value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::status = value;
  }-*/;
  
  private static native java.lang.String getType(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::type;
  }-*/;
  
  private static native void setType(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::type = value;
  }-*/;
  
  private static native java.lang.String getUri(de.l3s.forgetit.model.Resources instance) /*-{
    return instance.@de.l3s.forgetit.model.Resources::uri;
  }-*/;
  
  private static native void setUri(de.l3s.forgetit.model.Resources instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.Resources::uri = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, de.l3s.forgetit.model.Resources instance) throws SerializationException {
    setAuthor(instance, streamReader.readString());
    setCorrectMB(instance, streamReader.readDouble());
    setCorrectPV(instance, streamReader.readDouble());
    setGenMB(instance, streamReader.readDouble());
    setGenPV(instance, streamReader.readString());
    setId(instance, streamReader.readInt());
    setName(instance, streamReader.readString());
    setPublishedDate(instance, streamReader.readString());
    setRuleSet(instance, streamReader.readString());
    setScenario(instance, streamReader.readString());
    setSemanticType(instance, streamReader.readString());
    setStatus(instance, streamReader.readBoolean());
    setType(instance, streamReader.readString());
    setUri(instance, streamReader.readString());
    
  }
  
  public static de.l3s.forgetit.model.Resources instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new de.l3s.forgetit.model.Resources();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.l3s.forgetit.model.Resources instance) throws SerializationException {
    streamWriter.writeString(getAuthor(instance));
    streamWriter.writeDouble(getCorrectMB(instance));
    streamWriter.writeDouble(getCorrectPV(instance));
    streamWriter.writeDouble(getGenMB(instance));
    streamWriter.writeString(getGenPV(instance));
    streamWriter.writeInt(getId(instance));
    streamWriter.writeString(getName(instance));
    streamWriter.writeString(getPublishedDate(instance));
    streamWriter.writeString(getRuleSet(instance));
    streamWriter.writeString(getScenario(instance));
    streamWriter.writeString(getSemanticType(instance));
    streamWriter.writeBoolean(getStatus(instance));
    streamWriter.writeString(getType(instance));
    streamWriter.writeString(getUri(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.l3s.forgetit.model.Resources_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.l3s.forgetit.model.Resources_FieldSerializer.deserialize(reader, (de.l3s.forgetit.model.Resources)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.l3s.forgetit.model.Resources_FieldSerializer.serialize(writer, (de.l3s.forgetit.model.Resources)object);
  }
  
}
