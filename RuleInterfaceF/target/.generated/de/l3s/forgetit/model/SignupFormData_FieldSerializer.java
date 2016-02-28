package de.l3s.forgetit.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class SignupFormData_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getAge(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::age;
  }-*/;
  
  private static native void setAge(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::age = value;
  }-*/;
  
  private static native java.lang.String getEmail(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::email;
  }-*/;
  
  private static native void setEmail(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::email = value;
  }-*/;
  
  private static native java.lang.String getFirstName(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::firstName;
  }-*/;
  
  private static native void setFirstName(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::firstName = value;
  }-*/;
  
  private static native java.lang.String getLastName(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::lastName;
  }-*/;
  
  private static native void setLastName(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::lastName = value;
  }-*/;
  
  private static native java.lang.String getOrg(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::org;
  }-*/;
  
  private static native void setOrg(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::org = value;
  }-*/;
  
  private static native java.lang.String getOrgUnit(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::orgUnit;
  }-*/;
  
  private static native void setOrgUnit(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::orgUnit = value;
  }-*/;
  
  private static native java.lang.String getPassword(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::password;
  }-*/;
  
  private static native void setPassword(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::password = value;
  }-*/;
  
  private static native java.lang.String getPersonPref(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::personPref;
  }-*/;
  
  private static native void setPersonPref(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::personPref = value;
  }-*/;
  
  private static native java.lang.String getRole(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::role;
  }-*/;
  
  private static native void setRole(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::role = value;
  }-*/;
  
  private static native java.lang.String getSex(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::sex;
  }-*/;
  
  private static native void setSex(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::sex = value;
  }-*/;
  
  private static native java.lang.String getUserName(de.l3s.forgetit.model.SignupFormData instance) /*-{
    return instance.@de.l3s.forgetit.model.SignupFormData::userName;
  }-*/;
  
  private static native void setUserName(de.l3s.forgetit.model.SignupFormData instance, java.lang.String value) 
  /*-{
    instance.@de.l3s.forgetit.model.SignupFormData::userName = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, de.l3s.forgetit.model.SignupFormData instance) throws SerializationException {
    setAge(instance, streamReader.readString());
    setEmail(instance, streamReader.readString());
    setFirstName(instance, streamReader.readString());
    setLastName(instance, streamReader.readString());
    setOrg(instance, streamReader.readString());
    setOrgUnit(instance, streamReader.readString());
    setPassword(instance, streamReader.readString());
    setPersonPref(instance, streamReader.readString());
    setRole(instance, streamReader.readString());
    setSex(instance, streamReader.readString());
    setUserName(instance, streamReader.readString());
    
  }
  
  public static de.l3s.forgetit.model.SignupFormData instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new de.l3s.forgetit.model.SignupFormData();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, de.l3s.forgetit.model.SignupFormData instance) throws SerializationException {
    streamWriter.writeString(getAge(instance));
    streamWriter.writeString(getEmail(instance));
    streamWriter.writeString(getFirstName(instance));
    streamWriter.writeString(getLastName(instance));
    streamWriter.writeString(getOrg(instance));
    streamWriter.writeString(getOrgUnit(instance));
    streamWriter.writeString(getPassword(instance));
    streamWriter.writeString(getPersonPref(instance));
    streamWriter.writeString(getRole(instance));
    streamWriter.writeString(getSex(instance));
    streamWriter.writeString(getUserName(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return de.l3s.forgetit.model.SignupFormData_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    de.l3s.forgetit.model.SignupFormData_FieldSerializer.deserialize(reader, (de.l3s.forgetit.model.SignupFormData)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    de.l3s.forgetit.model.SignupFormData_FieldSerializer.serialize(writer, (de.l3s.forgetit.model.SignupFormData)object);
  }
  
}
