/*   1:    */package it.unimi.dsi.fastutil.floats;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.HashCommon;
/*   4:    */import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
/*   5:    */import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*   6:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*   7:    */import it.unimi.dsi.fastutil.objects.ObjectSet;
/*   8:    */import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*   9:    */import java.io.Serializable;
/*  10:    */import java.util.Iterator;
/*  11:    */import java.util.Map;
/*  12:    */import java.util.Map.Entry;
/*  13:    */import java.util.Set;
/*  14:    */
/*  61:    */public abstract class AbstractFloat2ReferenceMap<V>
/*  62:    */  extends AbstractFloat2ReferenceFunction<V>
/*  63:    */  implements Float2ReferenceMap<V>, Serializable
/*  64:    */{
/*  65:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  66:    */  
/*  67:    */  public boolean containsValue(Object v)
/*  68:    */  {
/*  69: 69 */    return values().contains(v);
/*  70:    */  }
/*  71:    */  
/*  72:    */  public boolean containsKey(float k) {
/*  73: 73 */    return keySet().contains(k);
/*  74:    */  }
/*  75:    */  
/*  81:    */  public void putAll(Map<? extends Float, ? extends V> m)
/*  82:    */  {
/*  83: 83 */    int n = m.size();
/*  84: 84 */    Iterator<? extends Map.Entry<? extends Float, ? extends V>> i = m.entrySet().iterator();
/*  85: 85 */    if ((m instanceof Float2ReferenceMap))
/*  86:    */    {
/*  87: 87 */      while (n-- != 0) {
/*  88: 88 */        Float2ReferenceMap.Entry<? extends V> e = (Float2ReferenceMap.Entry)i.next();
/*  89: 89 */        put(e.getFloatKey(), e.getValue());
/*  90:    */      }
/*  91:    */      
/*  92:    */    }
/*  93:    */    else
/*  94: 94 */      while (n-- != 0) {
/*  95: 95 */        Map.Entry<? extends Float, ? extends V> e = (Map.Entry)i.next();
/*  96: 96 */        put((Float)e.getKey(), e.getValue());
/*  97:    */      }
/*  98:    */  }
/*  99:    */  
/* 100:    */  public boolean isEmpty() {
/* 101:101 */    return size() == 0;
/* 102:    */  }
/* 103:    */  
/* 105:    */  public static class BasicEntry<V>
/* 106:    */    implements Float2ReferenceMap.Entry<V>
/* 107:    */  {
/* 108:    */    protected float key;
/* 109:    */    
/* 110:    */    protected V value;
/* 111:    */    
/* 112:    */    public BasicEntry(Float key, V value)
/* 113:    */    {
/* 114:114 */      this.key = key.floatValue();
/* 115:115 */      this.value = value;
/* 116:    */    }
/* 117:    */    
/* 119:    */    public BasicEntry(float key, V value)
/* 120:    */    {
/* 121:121 */      this.key = key;
/* 122:122 */      this.value = value;
/* 123:    */    }
/* 124:    */    
/* 126:    */    public Float getKey()
/* 127:    */    {
/* 128:128 */      return Float.valueOf(this.key);
/* 129:    */    }
/* 130:    */    
/* 131:    */    public float getFloatKey()
/* 132:    */    {
/* 133:133 */      return this.key;
/* 134:    */    }
/* 135:    */    
/* 136:    */    public V getValue()
/* 137:    */    {
/* 138:138 */      return this.value;
/* 139:    */    }
/* 140:    */    
/* 148:148 */    public V setValue(V value) { throw new UnsupportedOperationException(); }
/* 149:    */    
/* 150:    */    public boolean equals(Object o) {
/* 151:151 */      if (!(o instanceof Map.Entry)) return false;
/* 152:152 */      Map.Entry<?, ?> e = (Map.Entry)o;
/* 153:153 */      return (this.key == ((Float)e.getKey()).floatValue()) && (this.value == e.getValue());
/* 154:    */    }
/* 155:    */    
/* 156:156 */    public int hashCode() { return HashCommon.float2int(this.key) ^ (this.value == null ? 0 : System.identityHashCode(this.value)); }
/* 157:    */    
/* 158:    */    public String toString() {
/* 159:159 */      return this.key + "->" + this.value;
/* 160:    */    }
/* 161:    */  }
/* 162:    */  
/* 172:    */  public FloatSet keySet()
/* 173:    */  {
/* 174:174 */    new AbstractFloatSet() {
/* 175:175 */      public boolean contains(float k) { return AbstractFloat2ReferenceMap.this.containsKey(k); }
/* 176:176 */      public int size() { return AbstractFloat2ReferenceMap.this.size(); }
/* 177:177 */      public void clear() { AbstractFloat2ReferenceMap.this.clear(); }
/* 178:    */      
/* 179:179 */      public FloatIterator iterator() { new AbstractFloatIterator() {
/* 180:180 */          final ObjectIterator<Map.Entry<Float, V>> i = AbstractFloat2ReferenceMap.this.entrySet().iterator();
/* 181:181 */          public float nextFloat() { return ((Float2ReferenceMap.Entry)this.i.next()).getFloatKey(); }
/* 182:182 */          public boolean hasNext() { return this.i.hasNext(); }
/* 183:    */        }; }
/* 184:    */    };
/* 185:    */  }
/* 186:    */  
/* 197:    */  public ReferenceCollection<V> values()
/* 198:    */  {
/* 199:199 */    new AbstractReferenceCollection() {
/* 200:200 */      public boolean contains(Object k) { return AbstractFloat2ReferenceMap.this.containsValue(k); }
/* 201:201 */      public int size() { return AbstractFloat2ReferenceMap.this.size(); }
/* 202:202 */      public void clear() { AbstractFloat2ReferenceMap.this.clear(); }
/* 203:    */      
/* 204:204 */      public ObjectIterator<V> iterator() { new AbstractObjectIterator() {
/* 205:205 */          final ObjectIterator<Map.Entry<Float, V>> i = AbstractFloat2ReferenceMap.this.entrySet().iterator();
/* 206:206 */          public V next() { return ((Float2ReferenceMap.Entry)this.i.next()).getValue(); }
/* 207:207 */          public boolean hasNext() { return this.i.hasNext(); }
/* 208:    */        }; }
/* 209:    */    };
/* 210:    */  }
/* 211:    */  
/* 212:    */  public ObjectSet<Map.Entry<Float, V>> entrySet()
/* 213:    */  {
/* 214:214 */    return float2ReferenceEntrySet();
/* 215:    */  }
/* 216:    */  
/* 221:    */  public int hashCode()
/* 222:    */  {
/* 223:223 */    int h = 0;int n = size();
/* 224:224 */    ObjectIterator<? extends Map.Entry<Float, V>> i = entrySet().iterator();
/* 225:225 */    while (n-- != 0) h += ((Map.Entry)i.next()).hashCode();
/* 226:226 */    return h;
/* 227:    */  }
/* 228:    */  
/* 229:229 */  public boolean equals(Object o) { if (o == this) return true;
/* 230:230 */    if (!(o instanceof Map)) return false;
/* 231:231 */    Map<?, ?> m = (Map)o;
/* 232:232 */    if (m.size() != size()) return false;
/* 233:233 */    return entrySet().containsAll(m.entrySet());
/* 234:    */  }
/* 235:    */  
/* 236:236 */  public String toString() { StringBuilder s = new StringBuilder();
/* 237:237 */    ObjectIterator<? extends Map.Entry<Float, V>> i = entrySet().iterator();
/* 238:238 */    int n = size();
/* 239:    */    
/* 240:240 */    boolean first = true;
/* 241:241 */    s.append("{");
/* 242:242 */    while (n-- != 0) {
/* 243:243 */      if (first) first = false; else
/* 244:244 */        s.append(", ");
/* 245:245 */      Float2ReferenceMap.Entry<V> e = (Float2ReferenceMap.Entry)i.next();
/* 246:246 */      s.append(String.valueOf(e.getFloatKey()));
/* 247:247 */      s.append("=>");
/* 248:248 */      if (this == e.getValue()) s.append("(this map)"); else
/* 249:249 */        s.append(String.valueOf(e.getValue()));
/* 250:    */    }
/* 251:251 */    s.append("}");
/* 252:252 */    return s.toString();
/* 253:    */  }
/* 254:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.floats.AbstractFloat2ReferenceMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */