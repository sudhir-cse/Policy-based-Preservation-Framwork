package de.l3s.forgetit.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class QuestionData_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getAnswers(de.l3s.forgetit.model.QuestionData instance) /*-{
    return instance.@de.l3s.forgetit.model.QuestionData::answers;
  }-*/;
  
  private static native void setAnswers(de.l3s.forgetit.model.QuestionData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.QuestionData::answers = value;
  }-*/;
  
  private static native int getDefaultOption(de.l3s.forgetit.model.QuestionData instance) /*-{
    return instance.@de.l3s.forgetit.model.QuestionData::defaultOption;
  }-*/;
  
  private static native void setDefaultOption(de.l3s.forgetit.model.QuestionData instance, int value) 
  /*-{
    instance.@de.l3s.forgetit.model.QuestionData::defaultOption = value;
  }-*/;
  
  private static native java.util.HashMap getOptions(de.l3s.forgetit.model.QuestionData instance) /*-{
    return instance.@de.l3s.forgetit.model.QuestionData::options;
  }-*/;
  
  private static native void setOptions(de.l3s.forgetit.model.QuestionData instance, java.util.HashMap value) 
  /*-{
    instance.@de.l3s.forgetit.model.QuestionData::options = value;
  }-*/;
  
  private static native java.lang.String getQuestion(de.l3s.forgetit.model.QuestionData instance) /*-{
    return instance.@de.l3s.forgetit.model.QuestionData::question;
  }-*/;
  
  private static native void setQuestion(de.l3s.forgetit.model.QuestionData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.QuestionData::question = value;
  }-*/;
  
  private static native int getQuestionID(de.l3s.forgetit.model.QuestionData instance) /*-{
    return instance.@de.l3s.forgetit.model.QuestionData::questionID;
  }-*/;
  
  private static native void setQuestionID(de.l3s.forgetit.model.QuestionData instance, int value) 
  /*-{
    instance.@de.l3s.forgetit.model.QuestionData::questionID = value;
  }-*/;
  
  private static native int getQuestionNo(de.l3s.forgetit.model.QuestionData instance) /*-{
    return instance.@de.l3s.forgetit.model.QuestionData::questionNo;
  }-*/;
  
  private static native void setQuestionNo(de.l3s.forgetit.model.QuestionData instance, int value) 
  /*-{
    instance.@de.l3s.forgetit.model.QuestionData::questionNo = value;
  }-*/;
  
  private static native java.lang.String getScenarioName(de.l3s.forgetit.model.QuestionData instance) /*-{
    return instance.@de.l3s.forgetit.model.QuestionData::scenarioName;
  }-*/;
  
  private static native void setScenarioName(de.l3s.forgetit.model.QuestionData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.QuestionData::scenarioName = value;
  }-*/;
  
  private static native java.lang.String getType(de.l3s.forgetit.model.QuestionData instance) /*-{
    return instance.@de.l3s.forgetit.model.QuestionData::type;
  }-*/;
  
  private static native void setType(de.l3s.forgetit.model.QuestionData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.QuestionData::type = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, de.l3s.forgetit.model.QuestionData instance) throws SerializationException {
    setAnswers(instance, streamReader.readString());
    setDefaultOption(instance, streamReader.readInt());
    setOptions(instance, (java.util.HashMap) streamReader.readObject());
    setQuestion(instance, streamReader.readString());
    setQuestionID(instance, streamReader.readInt());
    setQuestionNo(instance, streamReader.readInt());
    setScenarioName(instance, streamReader.readString());
    setType(instance, streamReader.readString());
    
  }
  
  public static de.l3s.forgetit.model.QuestionData instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new de.l3s.forgetit.model.QuestionData();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.l3s.forgetit.model.QuestionData instance) throws SerializationException {
    streamWriter.writeString(getAnswers(instance));
    streamWriter.writeInt(getDefaultOption(instance));
    streamWriter.writeObject(getOptions(instance));
    streamWriter.writeString(getQuestion(instance));
    streamWriter.writeInt(getQuestionID(instance));
    streamWriter.writeInt(getQuestionNo(instance));
    streamWriter.writeString(getScenarioName(instance));
    streamWriter.writeString(getType(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.l3s.forgetit.model.QuestionData_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.l3s.forgetit.model.QuestionData_FieldSerializer.deserialize(reader, (de.l3s.forgetit.model.QuestionData)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.l3s.forgetit.model.QuestionData_FieldSerializer.serialize(writer, (de.l3s.forgetit.model.QuestionData)object);
  }
  
}
