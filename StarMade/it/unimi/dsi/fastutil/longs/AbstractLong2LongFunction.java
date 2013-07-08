/*   1:    */package it.unimi.dsi.fastutil.longs;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */
/*  64:    */public abstract class AbstractLong2LongFunction
/*  65:    */  implements Long2LongFunction, Serializable
/*  66:    */{
/*  67:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  68:    */  protected long defRetValue;
/*  69:    */  
/*  70:    */  public void defaultReturnValue(long rv)
/*  71:    */  {
/*  72: 72 */    this.defRetValue = rv;
/*  73:    */  }
/*  74:    */  
/*  75: 75 */  public long defaultReturnValue() { return this.defRetValue; }
/*  76:    */  
/*  77:    */  public long put(long key, long value) {
/*  78: 78 */    throw new UnsupportedOperationException();
/*  79:    */  }
/*  80:    */  
/*  81: 81 */  public long remove(long key) { throw new UnsupportedOperationException(); }
/*  82:    */  
/*  83:    */  public void clear() {
/*  84: 84 */    throw new UnsupportedOperationException();
/*  85:    */  }
/*  86:    */  
/*  87: 87 */  public boolean containsKey(Object ok) { return containsKey(((Long)ok).longValue()); }
/*  88:    */  
/*  93:    */  public Long get(Object ok)
/*  94:    */  {
/*  95: 95 */    long k = ((Long)ok).longValue();
/*  96: 96 */    return containsKey(k) ? Long.valueOf(get(k)) : null;
/*  97:    */  }
/*  98:    */  
/* 102:    */  public Long put(Long ok, Long ov)
/* 103:    */  {
/* 104:104 */    long k = ok.longValue();
/* 105:105 */    boolean containsKey = containsKey(k);
/* 106:106 */    long v = put(k, ov.longValue());
/* 107:107 */    return containsKey ? Long.valueOf(v) : null;
/* 108:    */  }
/* 109:    */  
/* 113:    */  public Long remove(Object ok)
/* 114:    */  {
/* 115:115 */    long k = ((Long)ok).longValue();
/* 116:116 */    boolean containsKey = containsKey(k);
/* 117:117 */    long v = remove(k);
/* 118:118 */    return containsKey ? Long.valueOf(v) : null;
/* 119:    */  }
/* 120:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.longs.AbstractLong2LongFunction
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */