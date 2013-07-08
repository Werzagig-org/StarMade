/*   1:    */package it.unimi.dsi.fastutil.shorts;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
/*   4:    */import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*   5:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*   6:    */import java.io.IOException;
/*   7:    */import java.io.ObjectInputStream;
/*   8:    */import java.io.ObjectOutputStream;
/*   9:    */import java.io.Serializable;
/*  10:    */import java.util.Map;
/*  11:    */import java.util.Map.Entry;
/*  12:    */import java.util.NoSuchElementException;
/*  13:    */
/*  66:    */public class Short2ShortArrayMap
/*  67:    */  extends AbstractShort2ShortMap
/*  68:    */  implements Serializable, Cloneable
/*  69:    */{
/*  70:    */  private static final long serialVersionUID = 1L;
/*  71:    */  private transient short[] key;
/*  72:    */  private transient short[] value;
/*  73:    */  private int size;
/*  74:    */  
/*  75:    */  public Short2ShortArrayMap(short[] key, short[] value)
/*  76:    */  {
/*  77: 77 */    this.key = key;
/*  78: 78 */    this.value = value;
/*  79: 79 */    this.size = key.length;
/*  80: 80 */    if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*  81:    */  }
/*  82:    */  
/*  83:    */  public Short2ShortArrayMap()
/*  84:    */  {
/*  85: 85 */    this.key = ShortArrays.EMPTY_ARRAY;
/*  86: 86 */    this.value = ShortArrays.EMPTY_ARRAY;
/*  87:    */  }
/*  88:    */  
/*  91:    */  public Short2ShortArrayMap(int capacity)
/*  92:    */  {
/*  93: 93 */    this.key = new short[capacity];
/*  94: 94 */    this.value = new short[capacity];
/*  95:    */  }
/*  96:    */  
/*  99:    */  public Short2ShortArrayMap(Short2ShortMap m)
/* 100:    */  {
/* 101:101 */    this(m.size());
/* 102:102 */    putAll(m);
/* 103:    */  }
/* 104:    */  
/* 107:    */  public Short2ShortArrayMap(Map<? extends Short, ? extends Short> m)
/* 108:    */  {
/* 109:109 */    this(m.size());
/* 110:110 */    putAll(m);
/* 111:    */  }
/* 112:    */  
/* 119:    */  public Short2ShortArrayMap(short[] key, short[] value, int size)
/* 120:    */  {
/* 121:121 */    this.key = key;
/* 122:122 */    this.value = value;
/* 123:123 */    this.size = size;
/* 124:124 */    if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/* 125:125 */    if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
/* 126:    */  }
/* 127:    */  
/* 128:    */  private final class EntrySet extends AbstractObjectSet<Short2ShortMap.Entry> implements Short2ShortMap.FastEntrySet { private EntrySet() {}
/* 129:    */    
/* 130:130 */    public ObjectIterator<Short2ShortMap.Entry> iterator() { new AbstractObjectIterator() {
/* 131:131 */        int next = 0;
/* 132:    */        
/* 133:133 */        public boolean hasNext() { return this.next < Short2ShortArrayMap.this.size; }
/* 134:    */        
/* 135:    */        public Short2ShortMap.Entry next()
/* 136:    */        {
/* 137:137 */          if (!hasNext()) throw new NoSuchElementException();
/* 138:138 */          return new AbstractShort2ShortMap.BasicEntry(Short2ShortArrayMap.this.key[this.next], Short2ShortArrayMap.this.value[(this.next++)]);
/* 139:    */        }
/* 140:    */      }; }
/* 141:    */    
/* 142:    */    public ObjectIterator<Short2ShortMap.Entry> fastIterator() {
/* 143:143 */      new AbstractObjectIterator() {
/* 144:144 */        int next = 0;
/* 145:145 */        final AbstractShort2ShortMap.BasicEntry entry = new AbstractShort2ShortMap.BasicEntry((short)0, (short)0);
/* 146:    */        
/* 147:147 */        public boolean hasNext() { return this.next < Short2ShortArrayMap.this.size; }
/* 148:    */        
/* 149:    */        public Short2ShortMap.Entry next()
/* 150:    */        {
/* 151:151 */          if (!hasNext()) throw new NoSuchElementException();
/* 152:152 */          this.entry.key = Short2ShortArrayMap.this.key[this.next];
/* 153:153 */          this.entry.value = Short2ShortArrayMap.this.value[(this.next++)];
/* 154:154 */          return this.entry;
/* 155:    */        }
/* 156:    */      };
/* 157:    */    }
/* 158:    */    
/* 159:159 */    public int size() { return Short2ShortArrayMap.this.size; }
/* 160:    */    
/* 161:    */    public boolean contains(Object o)
/* 162:    */    {
/* 163:163 */      if (!(o instanceof Map.Entry)) return false;
/* 164:164 */      Map.Entry<Short, Short> e = (Map.Entry)o;
/* 165:165 */      short k = ((Short)e.getKey()).shortValue();
/* 166:166 */      return (Short2ShortArrayMap.this.containsKey(k)) && (Short2ShortArrayMap.this.get(k) == ((Short)e.getValue()).shortValue());
/* 167:    */    }
/* 168:    */  }
/* 169:    */  
/* 170:170 */  public Short2ShortMap.FastEntrySet short2ShortEntrySet() { return new EntrySet(null); }
/* 171:    */  
/* 172:    */  private int findKey(short k) {
/* 173:173 */    short[] key = this.key;
/* 174:174 */    for (int i = this.size; i-- != 0;) if (key[i] == k) return i;
/* 175:175 */    return -1;
/* 176:    */  }
/* 177:    */  
/* 182:    */  public short get(short k)
/* 183:    */  {
/* 184:184 */    short[] key = this.key;
/* 185:185 */    for (int i = this.size; i-- != 0;) if (key[i] == k) return this.value[i];
/* 186:186 */    return this.defRetValue;
/* 187:    */  }
/* 188:    */  
/* 189:    */  public int size() {
/* 190:190 */    return this.size;
/* 191:    */  }
/* 192:    */  
/* 193:    */  public void clear()
/* 194:    */  {
/* 195:195 */    this.size = 0;
/* 196:    */  }
/* 197:    */  
/* 198:    */  public boolean containsKey(short k) {
/* 199:199 */    return findKey(k) != -1;
/* 200:    */  }
/* 201:    */  
/* 202:    */  public boolean containsValue(short v)
/* 203:    */  {
/* 204:204 */    for (int i = this.size; i-- != 0;) if (this.value[i] == v) return true;
/* 205:205 */    return false;
/* 206:    */  }
/* 207:    */  
/* 208:    */  public boolean isEmpty() {
/* 209:209 */    return this.size == 0;
/* 210:    */  }
/* 211:    */  
/* 212:    */  public short put(short k, short v)
/* 213:    */  {
/* 214:214 */    int oldKey = findKey(k);
/* 215:215 */    if (oldKey != -1) {
/* 216:216 */      short oldValue = this.value[oldKey];
/* 217:217 */      this.value[oldKey] = v;
/* 218:218 */      return oldValue;
/* 219:    */    }
/* 220:220 */    if (this.size == this.key.length) {
/* 221:221 */      short[] newKey = new short[this.size == 0 ? 2 : this.size * 2];
/* 222:222 */      short[] newValue = new short[this.size == 0 ? 2 : this.size * 2];
/* 223:223 */      for (int i = this.size; i-- != 0;) {
/* 224:224 */        newKey[i] = this.key[i];
/* 225:225 */        newValue[i] = this.value[i];
/* 226:    */      }
/* 227:227 */      this.key = newKey;
/* 228:228 */      this.value = newValue;
/* 229:    */    }
/* 230:230 */    this.key[this.size] = k;
/* 231:231 */    this.value[this.size] = v;
/* 232:232 */    this.size += 1;
/* 233:233 */    return this.defRetValue;
/* 234:    */  }
/* 235:    */  
/* 236:    */  public short remove(short k)
/* 237:    */  {
/* 238:238 */    int oldPos = findKey(k);
/* 239:239 */    if (oldPos == -1) return this.defRetValue;
/* 240:240 */    short oldValue = this.value[oldPos];
/* 241:241 */    int tail = this.size - oldPos - 1;
/* 242:242 */    for (int i = 0; i < tail; i++) {
/* 243:243 */      this.key[(oldPos + i)] = this.key[(oldPos + i + 1)];
/* 244:244 */      this.value[(oldPos + i)] = this.value[(oldPos + i + 1)];
/* 245:    */    }
/* 246:246 */    this.size -= 1;
/* 247:247 */    return oldValue;
/* 248:    */  }
/* 249:    */  
/* 250:    */  public ShortSet keySet()
/* 251:    */  {
/* 252:252 */    return new ShortArraySet(this.key, this.size);
/* 253:    */  }
/* 254:    */  
/* 255:    */  public ShortCollection values() {
/* 256:256 */    return ShortCollections.unmodifiable(new ShortArraySet(this.value, this.size));
/* 257:    */  }
/* 258:    */  
/* 262:    */  public Short2ShortArrayMap clone()
/* 263:    */  {
/* 264:    */    Short2ShortArrayMap c;
/* 265:    */    
/* 267:    */    try
/* 268:    */    {
/* 269:269 */      c = (Short2ShortArrayMap)super.clone();
/* 270:    */    }
/* 271:    */    catch (CloneNotSupportedException cantHappen) {
/* 272:272 */      throw new InternalError();
/* 273:    */    }
/* 274:274 */    c.key = ((short[])this.key.clone());
/* 275:275 */    c.value = ((short[])this.value.clone());
/* 276:276 */    return c;
/* 277:    */  }
/* 278:    */  
/* 279:279 */  private void writeObject(ObjectOutputStream s) throws IOException { s.defaultWriteObject();
/* 280:280 */    for (int i = 0; i < this.size; i++) {
/* 281:281 */      s.writeShort(this.key[i]);
/* 282:282 */      s.writeShort(this.value[i]);
/* 283:    */    }
/* 284:    */  }
/* 285:    */  
/* 286:    */  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 287:287 */    s.defaultReadObject();
/* 288:288 */    this.key = new short[this.size];
/* 289:289 */    this.value = new short[this.size];
/* 290:290 */    for (int i = 0; i < this.size; i++) {
/* 291:291 */      this.key[i] = s.readShort();
/* 292:292 */      this.value[i] = s.readShort();
/* 293:    */    }
/* 294:    */  }
/* 295:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.shorts.Short2ShortArrayMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */