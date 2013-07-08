/*   1:    */package it.unimi.dsi.fastutil.floats;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*   4:    */import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
/*   5:    */import java.io.Serializable;
/*   6:    */import java.util.Comparator;
/*   7:    */import java.util.Map.Entry;
/*   8:    */import java.util.NoSuchElementException;
/*   9:    */
/*  59:    */public class Float2ObjectSortedMaps
/*  60:    */{
/*  61:    */  public static Comparator<? super Map.Entry<Float, ?>> entryComparator(FloatComparator comparator)
/*  62:    */  {
/*  63: 63 */    new Comparator() {
/*  64:    */      public int compare(Map.Entry<Float, ?> x, Map.Entry<Float, ?> y) {
/*  65: 65 */        return this.val$comparator.compare(x.getKey(), y.getKey());
/*  66:    */      }
/*  67:    */    };
/*  68:    */  }
/*  69:    */  
/*  71:    */  public static class EmptySortedMap<V>
/*  72:    */    extends Float2ObjectMaps.EmptyMap<V>
/*  73:    */    implements Float2ObjectSortedMap<V>, Serializable, Cloneable
/*  74:    */  {
/*  75:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  76:    */    
/*  77: 77 */    public FloatComparator comparator() { return null; }
/*  78:    */    
/*  79: 79 */    public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() { return ObjectSortedSets.EMPTY_SET; }
/*  80:    */    
/*  81: 81 */    public ObjectSortedSet<Map.Entry<Float, V>> entrySet() { return ObjectSortedSets.EMPTY_SET; }
/*  82:    */    
/*  83: 83 */    public FloatSortedSet keySet() { return FloatSortedSets.EMPTY_SET; }
/*  84:    */    
/*  85: 85 */    public Float2ObjectSortedMap<V> subMap(float from, float to) { return Float2ObjectSortedMaps.EMPTY_MAP; }
/*  86:    */    
/*  87: 87 */    public Float2ObjectSortedMap<V> headMap(float to) { return Float2ObjectSortedMaps.EMPTY_MAP; }
/*  88:    */    
/*  89: 89 */    public Float2ObjectSortedMap<V> tailMap(float from) { return Float2ObjectSortedMaps.EMPTY_MAP; }
/*  90: 90 */    public float firstFloatKey() { throw new NoSuchElementException(); }
/*  91: 91 */    public float lastFloatKey() { throw new NoSuchElementException(); }
/*  92: 92 */    public Float2ObjectSortedMap<V> headMap(Float oto) { return headMap(oto.floatValue()); }
/*  93: 93 */    public Float2ObjectSortedMap<V> tailMap(Float ofrom) { return tailMap(ofrom.floatValue()); }
/*  94: 94 */    public Float2ObjectSortedMap<V> subMap(Float ofrom, Float oto) { return subMap(ofrom.floatValue(), oto.floatValue()); }
/*  95: 95 */    public Float firstKey() { return Float.valueOf(firstFloatKey()); }
/*  96: 96 */    public Float lastKey() { return Float.valueOf(lastFloatKey()); }
/*  97:    */  }
/*  98:    */  
/* 103:103 */  public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/* 104:    */  
/* 107:    */  public static class Singleton<V>
/* 108:    */    extends Float2ObjectMaps.Singleton<V>
/* 109:    */    implements Float2ObjectSortedMap<V>, Serializable, Cloneable
/* 110:    */  {
/* 111:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 112:    */    
/* 114:    */    protected final FloatComparator comparator;
/* 115:    */    
/* 117:    */    protected Singleton(float key, V value, FloatComparator comparator)
/* 118:    */    {
/* 119:119 */      super(value);
/* 120:120 */      this.comparator = comparator;
/* 121:    */    }
/* 122:    */    
/* 123:    */    protected Singleton(float key, V value) {
/* 124:124 */      this(key, value, null);
/* 125:    */    }
/* 126:    */    
/* 127:    */    final int compare(float k1, float k2)
/* 128:    */    {
/* 129:129 */      return this.comparator == null ? 1 : k1 == k2 ? 0 : k1 < k2 ? -1 : this.comparator.compare(k1, k2);
/* 130:    */    }
/* 131:    */    
/* 132:132 */    public FloatComparator comparator() { return this.comparator; }
/* 133:    */    
/* 134:    */    public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 135:135 */      if (this.entries == null) this.entries = ObjectSortedSets.singleton(new Float2ObjectMaps.Singleton.SingletonEntry(this), Float2ObjectSortedMaps.entryComparator(this.comparator)); return (ObjectSortedSet)this.entries; }
/* 136:    */    
/* 137:137 */    public ObjectSortedSet<Map.Entry<Float, V>> entrySet() { return float2ObjectEntrySet(); }
/* 138:    */    
/* 139:139 */    public FloatSortedSet keySet() { if (this.keys == null) this.keys = FloatSortedSets.singleton(this.key, this.comparator); return (FloatSortedSet)this.keys;
/* 140:    */    }
/* 141:    */    
/* 142:142 */    public Float2ObjectSortedMap<V> subMap(float from, float to) { if ((compare(from, this.key) <= 0) && (compare(this.key, to) < 0)) return this; return Float2ObjectSortedMaps.EMPTY_MAP;
/* 143:    */    }
/* 144:    */    
/* 145:145 */    public Float2ObjectSortedMap<V> headMap(float to) { if (compare(this.key, to) < 0) return this; return Float2ObjectSortedMaps.EMPTY_MAP;
/* 146:    */    }
/* 147:    */    
/* 148:148 */    public Float2ObjectSortedMap<V> tailMap(float from) { if (compare(from, this.key) <= 0) return this; return Float2ObjectSortedMaps.EMPTY_MAP; }
/* 149:    */    
/* 150:150 */    public float firstFloatKey() { return this.key; }
/* 151:151 */    public float lastFloatKey() { return this.key; }
/* 152:    */    
/* 154:154 */    public Float2ObjectSortedMap<V> headMap(Float oto) { return headMap(oto.floatValue()); }
/* 155:155 */    public Float2ObjectSortedMap<V> tailMap(Float ofrom) { return tailMap(ofrom.floatValue()); }
/* 156:156 */    public Float2ObjectSortedMap<V> subMap(Float ofrom, Float oto) { return subMap(ofrom.floatValue(), oto.floatValue()); }
/* 157:    */    
/* 158:158 */    public Float firstKey() { return Float.valueOf(firstFloatKey()); }
/* 159:159 */    public Float lastKey() { return Float.valueOf(lastFloatKey()); }
/* 160:    */  }
/* 161:    */  
/* 171:    */  public static <V> Float2ObjectSortedMap<V> singleton(Float key, V value)
/* 172:    */  {
/* 173:173 */    return new Singleton(key.floatValue(), value);
/* 174:    */  }
/* 175:    */  
/* 185:    */  public static <V> Float2ObjectSortedMap<V> singleton(Float key, V value, FloatComparator comparator)
/* 186:    */  {
/* 187:187 */    return new Singleton(key.floatValue(), value, comparator);
/* 188:    */  }
/* 189:    */  
/* 200:    */  public static <V> Float2ObjectSortedMap<V> singleton(float key, V value)
/* 201:    */  {
/* 202:202 */    return new Singleton(key, value);
/* 203:    */  }
/* 204:    */  
/* 214:    */  public static <V> Float2ObjectSortedMap<V> singleton(float key, V value, FloatComparator comparator)
/* 215:    */  {
/* 216:216 */    return new Singleton(key, value, comparator);
/* 217:    */  }
/* 218:    */  
/* 220:    */  public static class SynchronizedSortedMap<V>
/* 221:    */    extends Float2ObjectMaps.SynchronizedMap<V>
/* 222:    */    implements Float2ObjectSortedMap<V>, Serializable
/* 223:    */  {
/* 224:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 225:    */    
/* 226:    */    protected final Float2ObjectSortedMap<V> sortedMap;
/* 227:    */    
/* 229:    */    protected SynchronizedSortedMap(Float2ObjectSortedMap<V> m, Object sync)
/* 230:    */    {
/* 231:231 */      super(sync);
/* 232:232 */      this.sortedMap = m;
/* 233:    */    }
/* 234:    */    
/* 235:    */    protected SynchronizedSortedMap(Float2ObjectSortedMap<V> m) {
/* 236:236 */      super();
/* 237:237 */      this.sortedMap = m;
/* 238:    */    }
/* 239:    */    
/* 240:240 */    public FloatComparator comparator() { synchronized (this.sync) { return this.sortedMap.comparator(); } }
/* 241:    */    
/* 242:242 */    public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() { if (this.entries == null) this.entries = ObjectSortedSets.synchronize(this.sortedMap.float2ObjectEntrySet(), this.sync); return (ObjectSortedSet)this.entries; }
/* 243:    */    
/* 244:244 */    public ObjectSortedSet<Map.Entry<Float, V>> entrySet() { return float2ObjectEntrySet(); }
/* 245:245 */    public FloatSortedSet keySet() { if (this.keys == null) this.keys = FloatSortedSets.synchronize(this.sortedMap.keySet(), this.sync); return (FloatSortedSet)this.keys; }
/* 246:    */    
/* 247:247 */    public Float2ObjectSortedMap<V> subMap(float from, float to) { return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync); }
/* 248:248 */    public Float2ObjectSortedMap<V> headMap(float to) { return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync); }
/* 249:249 */    public Float2ObjectSortedMap<V> tailMap(float from) { return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync); }
/* 250:    */    
/* 251:251 */    public float firstFloatKey() { synchronized (this.sync) { return this.sortedMap.firstFloatKey(); } }
/* 252:252 */    public float lastFloatKey() { synchronized (this.sync) { return this.sortedMap.lastFloatKey();
/* 253:    */      } }
/* 254:    */    
/* 255:255 */    public Float firstKey() { synchronized (this.sync) { return (Float)this.sortedMap.firstKey(); } }
/* 256:256 */    public Float lastKey() { synchronized (this.sync) { return (Float)this.sortedMap.lastKey(); } }
/* 257:    */    
/* 258:258 */    public Float2ObjectSortedMap<V> subMap(Float from, Float to) { return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync); }
/* 259:259 */    public Float2ObjectSortedMap<V> headMap(Float to) { return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync); }
/* 260:260 */    public Float2ObjectSortedMap<V> tailMap(Float from) { return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync); }
/* 261:    */  }
/* 262:    */  
/* 270:    */  public static <V> Float2ObjectSortedMap<V> synchronize(Float2ObjectSortedMap<V> m)
/* 271:    */  {
/* 272:272 */    return new SynchronizedSortedMap(m);
/* 273:    */  }
/* 274:    */  
/* 280:    */  public static <V> Float2ObjectSortedMap<V> synchronize(Float2ObjectSortedMap<V> m, Object sync)
/* 281:    */  {
/* 282:282 */    return new SynchronizedSortedMap(m, sync);
/* 283:    */  }
/* 284:    */  
/* 286:    */  public static class UnmodifiableSortedMap<V>
/* 287:    */    extends Float2ObjectMaps.UnmodifiableMap<V>
/* 288:    */    implements Float2ObjectSortedMap<V>, Serializable
/* 289:    */  {
/* 290:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 291:    */    
/* 292:    */    protected final Float2ObjectSortedMap<V> sortedMap;
/* 293:    */    
/* 294:    */    protected UnmodifiableSortedMap(Float2ObjectSortedMap<V> m)
/* 295:    */    {
/* 296:296 */      super();
/* 297:297 */      this.sortedMap = m;
/* 298:    */    }
/* 299:    */    
/* 300:300 */    public FloatComparator comparator() { return this.sortedMap.comparator(); }
/* 301:    */    
/* 302:302 */    public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() { if (this.entries == null) this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.float2ObjectEntrySet()); return (ObjectSortedSet)this.entries; }
/* 303:    */    
/* 304:304 */    public ObjectSortedSet<Map.Entry<Float, V>> entrySet() { return float2ObjectEntrySet(); }
/* 305:305 */    public FloatSortedSet keySet() { if (this.keys == null) this.keys = FloatSortedSets.unmodifiable(this.sortedMap.keySet()); return (FloatSortedSet)this.keys; }
/* 306:    */    
/* 307:307 */    public Float2ObjectSortedMap<V> subMap(float from, float to) { return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to)); }
/* 308:308 */    public Float2ObjectSortedMap<V> headMap(float to) { return new UnmodifiableSortedMap(this.sortedMap.headMap(to)); }
/* 309:309 */    public Float2ObjectSortedMap<V> tailMap(float from) { return new UnmodifiableSortedMap(this.sortedMap.tailMap(from)); }
/* 310:    */    
/* 311:311 */    public float firstFloatKey() { return this.sortedMap.firstFloatKey(); }
/* 312:312 */    public float lastFloatKey() { return this.sortedMap.lastFloatKey(); }
/* 313:    */    
/* 315:315 */    public Float firstKey() { return (Float)this.sortedMap.firstKey(); }
/* 316:316 */    public Float lastKey() { return (Float)this.sortedMap.lastKey(); }
/* 317:    */    
/* 318:318 */    public Float2ObjectSortedMap<V> subMap(Float from, Float to) { return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to)); }
/* 319:319 */    public Float2ObjectSortedMap<V> headMap(Float to) { return new UnmodifiableSortedMap(this.sortedMap.headMap(to)); }
/* 320:320 */    public Float2ObjectSortedMap<V> tailMap(Float from) { return new UnmodifiableSortedMap(this.sortedMap.tailMap(from)); }
/* 321:    */  }
/* 322:    */  
/* 330:    */  public static <V> Float2ObjectSortedMap<V> unmodifiable(Float2ObjectSortedMap<V> m)
/* 331:    */  {
/* 332:332 */    return new UnmodifiableSortedMap(m);
/* 333:    */  }
/* 334:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.floats.Float2ObjectSortedMaps
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */