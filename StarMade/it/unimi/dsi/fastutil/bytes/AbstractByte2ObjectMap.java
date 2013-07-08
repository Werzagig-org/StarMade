/*   1:    */package it.unimi.dsi.fastutil.bytes;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*   4:    */import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
/*   5:    */import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*   6:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*   7:    */import it.unimi.dsi.fastutil.objects.ObjectSet;
/*   8:    */import java.io.Serializable;
/*   9:    */import java.util.Iterator;
/*  10:    */import java.util.Map;
/*  11:    */import java.util.Map.Entry;
/*  12:    */import java.util.Set;
/*  13:    */
/*  61:    */public abstract class AbstractByte2ObjectMap<V>
/*  62:    */  extends AbstractByte2ObjectFunction<V>
/*  63:    */  implements Byte2ObjectMap<V>, Serializable
/*  64:    */{
/*  65:    */  public static final long serialVersionUID = -4940583368468432370L;
/*  66:    */  
/*  67:    */  public boolean containsValue(Object v)
/*  68:    */  {
/*  69: 69 */    return values().contains(v);
/*  70:    */  }
/*  71:    */  
/*  72:    */  public boolean containsKey(byte k) {
/*  73: 73 */    return keySet().contains(k);
/*  74:    */  }
/*  75:    */  
/*  81:    */  public void putAll(Map<? extends Byte, ? extends V> m)
/*  82:    */  {
/*  83: 83 */    int n = m.size();
/*  84: 84 */    Iterator<? extends Map.Entry<? extends Byte, ? extends V>> i = m.entrySet().iterator();
/*  85: 85 */    if ((m instanceof Byte2ObjectMap))
/*  86:    */    {
/*  87: 87 */      while (n-- != 0) {
/*  88: 88 */        Byte2ObjectMap.Entry<? extends V> e = (Byte2ObjectMap.Entry)i.next();
/*  89: 89 */        put(e.getByteKey(), e.getValue());
/*  90:    */      }
/*  91:    */      
/*  92:    */    }
/*  93:    */    else
/*  94: 94 */      while (n-- != 0) {
/*  95: 95 */        Map.Entry<? extends Byte, ? extends V> e = (Map.Entry)i.next();
/*  96: 96 */        put((Byte)e.getKey(), e.getValue());
/*  97:    */      }
/*  98:    */  }
/*  99:    */  
/* 100:    */  public boolean isEmpty() {
/* 101:101 */    return size() == 0;
/* 102:    */  }
/* 103:    */  
/* 105:    */  public static class BasicEntry<V>
/* 106:    */    implements Byte2ObjectMap.Entry<V>
/* 107:    */  {
/* 108:    */    protected byte key;
/* 109:    */    
/* 110:    */    protected V value;
/* 111:    */    
/* 112:    */    public BasicEntry(Byte key, V value)
/* 113:    */    {
/* 114:114 */      this.key = key.byteValue();
/* 115:115 */      this.value = value;
/* 116:    */    }
/* 117:    */    
/* 119:    */    public BasicEntry(byte key, V value)
/* 120:    */    {
/* 121:121 */      this.key = key;
/* 122:122 */      this.value = value;
/* 123:    */    }
/* 124:    */    
/* 126:    */    public Byte getKey()
/* 127:    */    {
/* 128:128 */      return Byte.valueOf(this.key);
/* 129:    */    }
/* 130:    */    
/* 131:    */    public byte getByteKey()
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
/* 153:153 */      return (this.key == ((Byte)e.getKey()).byteValue()) && (this.value == null ? e.getValue() == null : this.value.equals(e.getValue()));
/* 154:    */    }
/* 155:    */    
/* 156:156 */    public int hashCode() { return this.key ^ (this.value == null ? 0 : this.value.hashCode()); }
/* 157:    */    
/* 158:    */    public String toString() {
/* 159:159 */      return this.key + "->" + this.value;
/* 160:    */    }
/* 161:    */  }
/* 162:    */  
/* 172:    */  public ByteSet keySet()
/* 173:    */  {
/* 174:174 */    new AbstractByteSet() {
/* 175:175 */      public boolean contains(byte k) { return AbstractByte2ObjectMap.this.containsKey(k); }
/* 176:176 */      public int size() { return AbstractByte2ObjectMap.this.size(); }
/* 177:177 */      public void clear() { AbstractByte2ObjectMap.this.clear(); }
/* 178:    */      
/* 179:179 */      public ByteIterator iterator() { new AbstractByteIterator() {
/* 180:180 */          final ObjectIterator<Map.Entry<Byte, V>> i = AbstractByte2ObjectMap.this.entrySet().iterator();
/* 181:181 */          public byte nextByte() { return ((Byte2ObjectMap.Entry)this.i.next()).getByteKey(); }
/* 182:182 */          public boolean hasNext() { return this.i.hasNext(); }
/* 183:    */        }; }
/* 184:    */    };
/* 185:    */  }
/* 186:    */  
/* 197:    */  public ObjectCollection<V> values()
/* 198:    */  {
/* 199:199 */    new AbstractObjectCollection() {
/* 200:200 */      public boolean contains(Object k) { return AbstractByte2ObjectMap.this.containsValue(k); }
/* 201:201 */      public int size() { return AbstractByte2ObjectMap.this.size(); }
/* 202:202 */      public void clear() { AbstractByte2ObjectMap.this.clear(); }
/* 203:    */      
/* 204:204 */      public ObjectIterator<V> iterator() { new AbstractObjectIterator() {
/* 205:205 */          final ObjectIterator<Map.Entry<Byte, V>> i = AbstractByte2ObjectMap.this.entrySet().iterator();
/* 206:206 */          public V next() { return ((Byte2ObjectMap.Entry)this.i.next()).getValue(); }
/* 207:207 */          public boolean hasNext() { return this.i.hasNext(); }
/* 208:    */        }; }
/* 209:    */    };
/* 210:    */  }
/* 211:    */  
/* 212:    */  public ObjectSet<Map.Entry<Byte, V>> entrySet()
/* 213:    */  {
/* 214:214 */    return byte2ObjectEntrySet();
/* 215:    */  }
/* 216:    */  
/* 221:    */  public int hashCode()
/* 222:    */  {
/* 223:223 */    int h = 0;int n = size();
/* 224:224 */    ObjectIterator<? extends Map.Entry<Byte, V>> i = entrySet().iterator();
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
/* 237:237 */    ObjectIterator<? extends Map.Entry<Byte, V>> i = entrySet().iterator();
/* 238:238 */    int n = size();
/* 239:    */    
/* 240:240 */    boolean first = true;
/* 241:241 */    s.append("{");
/* 242:242 */    while (n-- != 0) {
/* 243:243 */      if (first) first = false; else
/* 244:244 */        s.append(", ");
/* 245:245 */      Byte2ObjectMap.Entry<V> e = (Byte2ObjectMap.Entry)i.next();
/* 246:246 */      s.append(String.valueOf(e.getByteKey()));
/* 247:247 */      s.append("=>");
/* 248:248 */      if (this == e.getValue()) s.append("(this map)"); else
/* 249:249 */        s.append(String.valueOf(e.getValue()));
/* 250:    */    }
/* 251:251 */    s.append("}");
/* 252:252 */    return s.toString();
/* 253:    */  }
/* 254:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.bytes.AbstractByte2ObjectMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */