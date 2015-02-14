package com.voca.vocable.vo;

import java.io.Serializable;

public class VocaNote
  implements Serializable
{
  String days;
  String gubun;
  int index;
  String meaning;
  String saveYn;
  String synonym;
  String word;
  
  public String getDays()
  {
    return this.days;
  }
  
  public String getGubun()
  {
    return this.gubun;
  }
  
  public int getIndex()
  {
    return this.index;
  }
  
  public String getMeaning()
  {
    return this.meaning;
  }
  
  public String getSaveYn()
  {
    return this.saveYn;
  }
  
  public String getSynonym()
  {
    return this.synonym;
  }
  
  public String getWord()
  {
    return this.word;
  }
  
  public void setDays(String paramString)
  {
    this.days = paramString;
  }
  
  public void setGubun(String paramString)
  {
    this.gubun = paramString;
  }
  
  public void setIndex(int paramInt)
  {
    this.index = paramInt;
  }
  
  public void setMeaning(String paramString)
  {
    this.meaning = paramString;
  }
  
  public void setSaveYn(String paramString)
  {
    this.saveYn = paramString;
  }
  
  public void setSynonym(String paramString)
  {
    this.synonym = paramString;
  }
  
  public void setWord(String paramString)
  {
    this.word = paramString;
  }
}
