package de.l3s.forgetit.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class StringPair_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getLeft(de.l3s.forgetit.model.StringPair instance) /*-{
    return instance.@de.l3s.forgetit.model.StringPair::left;
  }-*/;
  
  private static native void setLeft(de.l3s.forgetit.model.StringPair instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.StringPair::left = value;
  }-*/;
  
  private static native java.lang.String getRight(de.l3s.forgetit.model.StringPair instance) /*-{
    return instance.@de.l3s.forgetit.model.StringPair::right;
  }-*/;
  
  private static native void setRight(de.l3s.forgetit.model.StringPair instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.StringPair::right = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, de.l3s.forgetit.model.StringPair instance) throws SerializationException {
    setLeft(instance, streamReader.readString());
    setRight(instance, streamReader.readString());
    
  }
  
  public static de.l3s.forgetit.model.StringPair instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new de.l3s.forgetit.model.StringPair();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.l3s.forgetit.model.StringPair instance) throws SerializationException {
    streamWriter.writeString(getLeft(instance));
    streamWriter.writeString(getRight(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.l3s.forgetit.model.StringPair_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.l3s.forgetit.model.StringPair_FieldSerializer.deserialize(reader, (de.l3s.forgetit.model.StringPair)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.l3s.forgetit.model.StringPair_FieldSerializer.serialize(writer, (de.l3s.forgetit.model.StringPair)object);
  }
  
}
