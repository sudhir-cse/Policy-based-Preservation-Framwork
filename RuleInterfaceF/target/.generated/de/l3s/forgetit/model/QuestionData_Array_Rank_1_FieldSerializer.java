package de.l3s.forgetit.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class QuestionData_Array_Rank_1_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, de.l3s.forgetit.model.QuestionData[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static de.l3s.forgetit.model.QuestionData[] instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int size = streamReader.readInt();
    return new de.l3s.forgetit.model.QuestionData[size];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.l3s.forgetit.model.QuestionData[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.l3s.forgetit.model.QuestionData_Array_Rank_1_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.l3s.forgetit.model.QuestionData_Array_Rank_1_FieldSerializer.deserialize(reader, (de.l3s.forgetit.model.QuestionData[])object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.l3s.forgetit.model.QuestionData_Array_Rank_1_FieldSerializer.serialize(writer, (de.l3s.forgetit.model.QuestionData[])object);
  }
  
}
