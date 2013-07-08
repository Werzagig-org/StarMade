/*   1:    */package it.unimi.dsi.fastutil.doubles;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */
/*  64:    */public abstract class AbstractDouble2DoubleFunction
/*  65:    */  implements Double2DoubleFunction, Serializable
/*  66:    */{
/*  67:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  68:    */  protected double defRetValue;
/*  69:    */  
/*  70:    */  public void defaultReturnValue(double rv)
/*  71:    */  {
/*  72: 72 */    this.defRetValue = rv;
/*  73:    */  }
/*  74:    */  
/*  75: 75 */  public double defaultReturnValue() { return this.defRetValue; }
/*  76:    */  
/*  77:    */  public double put(double key, double value) {
/*  78: 78 */    throw new UnsupportedOperationException();
/*  79:    */  }
/*  80:    */  
/*  81: 81 */  public double remove(double key) { throw new UnsupportedOperationException(); }
/*  82:    */  
/*  83:    */  public void clear() {
/*  84: 84 */    throw new UnsupportedOperationException();
/*  85:    */  }
/*  86:    */  
/*  87: 87 */  public boolean containsKey(Object ok) { return containsKey(((Double)ok).doubleValue()); }
/*  88:    */  
/*  93:    */  public Double get(Object ok)
/*  94:    */  {
/*  95: 95 */    double k = ((Double)ok).doubleValue();
/*  96: 96 */    return containsKey(k) ? Double.valueOf(get(k)) : null;
/*  97:    */  }
/*  98:    */  
/* 102:    */  public Double put(Double ok, Double ov)
/* 103:    */  {
/* 104:104 */    double k = ok.doubleValue();
/* 105:105 */    boolean containsKey = containsKey(k);
/* 106:106 */    double v = put(k, ov.doubleValue());
/* 107:107 */    return containsKey ? Double.valueOf(v) : null;
/* 108:    */  }
/* 109:    */  
/* 113:    */  public Double remove(Object ok)
/* 114:    */  {
/* 115:115 */    double k = ((Double)ok).doubleValue();
/* 116:116 */    boolean containsKey = containsKey(k);
/* 117:117 */    double v = remove(k);
/* 118:118 */    return containsKey ? Double.valueOf(v) : null;
/* 119:    */  }
/* 120:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleFunction
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */