/*   1:    */package it.unimi.dsi.fastutil.ints;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*   4:    */import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
/*   5:    */import java.io.Serializable;
/*   6:    */import java.util.Comparator;
/*   7:    */import java.util.Map.Entry;
/*   8:    */import java.util.NoSuchElementException;
/*   9:    */
/*  60:    */public class Int2ShortSortedMaps
/*  61:    */{
/*  62:    */  public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(IntComparator comparator)
/*  63:    */  {
/*  64: 64 */    new Comparator() {
/*  65:    */      public int compare(Map.Entry<Integer, ?> x, Map.Entry<Integer, ?> y) {
/*  66: 66 */        return this.val$comparator.compare(x.getKey(), y.getKey());
/*  67:    */      }
/*  68:    */    };
/*  69:    */  }
/*  70:    */  
/*  72:    */  public static class EmptySortedMap
/*  73:    */    extends Int2ShortMaps.EmptyMap
/*  74:    */    implements Int2ShortSortedMap, Serializable, Cloneable
/*  75:    */  {
/*  76:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  77:    */    
/*  78: 78 */    public IntComparator comparator() { return null; }
/*  79:    */    
/*  80: 80 */    public ObjectSortedSet<Int2ShortMap.Entry> int2ShortEntrySet() { return ObjectSortedSets.EMPTY_SET; }
/*  81:    */    
/*  82: 82 */    public ObjectSortedSet<Map.Entry<Integer, Short>> entrySet() { return ObjectSortedSets.EMPTY_SET; }
/*  83:    */    
/*  84: 84 */    public IntSortedSet keySet() { return IntSortedSets.EMPTY_SET; }
/*  85:    */    
/*  86: 86 */    public Int2ShortSortedMap subMap(int from, int to) { return Int2ShortSortedMaps.EMPTY_MAP; }
/*  87:    */    
/*  88: 88 */    public Int2ShortSortedMap headMap(int to) { return Int2ShortSortedMaps.EMPTY_MAP; }
/*  89:    */    
/*  90: 90 */    public Int2ShortSortedMap tailMap(int from) { return Int2ShortSortedMaps.EMPTY_MAP; }
/*  91: 91 */    public int firstIntKey() { throw new NoSuchElementException(); }
/*  92: 92 */    public int lastIntKey() { throw new NoSuchElementException(); }
/*  93: 93 */    public Int2ShortSortedMap headMap(Integer oto) { return headMap(oto.intValue()); }
/*  94: 94 */    public Int2ShortSortedMap tailMap(Integer ofrom) { return tailMap(ofrom.intValue()); }
/*  95: 95 */    public Int2ShortSortedMap subMap(Integer ofrom, Integer oto) { return subMap(ofrom.intValue(), oto.intValue()); }
/*  96: 96 */    public Integer firstKey() { return Integer.valueOf(firstIntKey()); }
/*  97: 97 */    public Integer lastKey() { return Integer.valueOf(lastIntKey()); }
/*  98:    */  }
/*  99:    */  
/* 103:103 */  public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/* 104:    */  
/* 107:    */  public static class Singleton
/* 108:    */    extends Int2ShortMaps.Singleton
/* 109:    */    implements Int2ShortSortedMap, Serializable, Cloneable
/* 110:    */  {
/* 111:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 112:    */    
/* 114:    */    protected final IntComparator comparator;
/* 115:    */    
/* 117:    */    protected Singleton(int key, short value, IntComparator comparator)
/* 118:    */    {
/* 119:119 */      super(value);
/* 120:120 */      this.comparator = comparator;
/* 121:    */    }
/* 122:    */    
/* 123:    */    protected Singleton(int key, short value) {
/* 124:124 */      this(key, value, null);
/* 125:    */    }
/* 126:    */    
/* 127:    */    final int compare(int k1, int k2)
/* 128:    */    {
/* 129:129 */      return this.comparator == null ? 1 : k1 == k2 ? 0 : k1 < k2 ? -1 : this.comparator.compare(k1, k2);
/* 130:    */    }
/* 131:    */    
/* 132:132 */    public IntComparator comparator() { return this.comparator; }
/* 133:    */    
/* 134:    */    public ObjectSortedSet<Int2ShortMap.Entry> int2ShortEntrySet() {
/* 135:135 */      if (this.entries == null) this.entries = ObjectSortedSets.singleton(new Int2ShortMaps.Singleton.SingletonEntry(this), Int2ShortSortedMaps.entryComparator(this.comparator)); return (ObjectSortedSet)this.entries; }
/* 136:    */    
/* 137:137 */    public ObjectSortedSet<Map.Entry<Integer, Short>> entrySet() { return int2ShortEntrySet(); }
/* 138:    */    
/* 139:139 */    public IntSortedSet keySet() { if (this.keys == null) this.keys = IntSortedSets.singleton(this.key, this.comparator); return (IntSortedSet)this.keys;
/* 140:    */    }
/* 141:    */    
/* 142:142 */    public Int2ShortSortedMap subMap(int from, int to) { if ((compare(from, this.key) <= 0) && (compare(this.key, to) < 0)) return this; return Int2ShortSortedMaps.EMPTY_MAP;
/* 143:    */    }
/* 144:    */    
/* 145:145 */    public Int2ShortSortedMap headMap(int to) { if (compare(this.key, to) < 0) return this; return Int2ShortSortedMaps.EMPTY_MAP;
/* 146:    */    }
/* 147:    */    
/* 148:148 */    public Int2ShortSortedMap tailMap(int from) { if (compare(from, this.key) <= 0) return this; return Int2ShortSortedMaps.EMPTY_MAP; }
/* 149:    */    
/* 150:150 */    public int firstIntKey() { return this.key; }
/* 151:151 */    public int lastIntKey() { return this.key; }
/* 152:    */    
/* 154:154 */    public Int2ShortSortedMap headMap(Integer oto) { return headMap(oto.intValue()); }
/* 155:155 */    public Int2ShortSortedMap tailMap(Integer ofrom) { return tailMap(ofrom.intValue()); }
/* 156:156 */    public Int2ShortSortedMap subMap(Integer ofrom, Integer oto) { return subMap(ofrom.intValue(), oto.intValue()); }
/* 157:    */    
/* 158:158 */    public Integer firstKey() { return Integer.valueOf(firstIntKey()); }
/* 159:159 */    public Integer lastKey() { return Integer.valueOf(lastIntKey()); }
/* 160:    */  }
/* 161:    */  
/* 171:    */  public static Int2ShortSortedMap singleton(Integer key, Short value)
/* 172:    */  {
/* 173:173 */    return new Singleton(key.intValue(), value.shortValue());
/* 174:    */  }
/* 175:    */  
/* 185:    */  public static Int2ShortSortedMap singleton(Integer key, Short value, IntComparator comparator)
/* 186:    */  {
/* 187:187 */    return new Singleton(key.intValue(), value.shortValue(), comparator);
/* 188:    */  }
/* 189:    */  
/* 200:    */  public static Int2ShortSortedMap singleton(int key, short value)
/* 201:    */  {
/* 202:202 */    return new Singleton(key, value);
/* 203:    */  }
/* 204:    */  
/* 214:    */  public static Int2ShortSortedMap singleton(int key, short value, IntComparator comparator)
/* 215:    */  {
/* 216:216 */    return new Singleton(key, value, comparator);
/* 217:    */  }
/* 218:    */  
/* 220:    */  public static class SynchronizedSortedMap
/* 221:    */    extends Int2ShortMaps.SynchronizedMap
/* 222:    */    implements Int2ShortSortedMap, Serializable
/* 223:    */  {
/* 224:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 225:    */    
/* 226:    */    protected final Int2ShortSortedMap sortedMap;
/* 227:    */    
/* 229:    */    protected SynchronizedSortedMap(Int2ShortSortedMap m, Object sync)
/* 230:    */    {
/* 231:231 */      super(sync);
/* 232:232 */      this.sortedMap = m;
/* 233:    */    }
/* 234:    */    
/* 235:    */    protected SynchronizedSortedMap(Int2ShortSortedMap m) {
/* 236:236 */      super();
/* 237:237 */      this.sortedMap = m;
/* 238:    */    }
/* 239:    */    
/* 240:240 */    public IntComparator comparator() { synchronized (this.sync) { return this.sortedMap.comparator(); } }
/* 241:    */    
/* 242:242 */    public ObjectSortedSet<Int2ShortMap.Entry> int2ShortEntrySet() { if (this.entries == null) this.entries = ObjectSortedSets.synchronize(this.sortedMap.int2ShortEntrySet(), this.sync); return (ObjectSortedSet)this.entries; }
/* 243:    */    
/* 244:244 */    public ObjectSortedSet<Map.Entry<Integer, Short>> entrySet() { return int2ShortEntrySet(); }
/* 245:245 */    public IntSortedSet keySet() { if (this.keys == null) this.keys = IntSortedSets.synchronize(this.sortedMap.keySet(), this.sync); return (IntSortedSet)this.keys; }
/* 246:    */    
/* 247:247 */    public Int2ShortSortedMap subMap(int from, int to) { return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync); }
/* 248:248 */    public Int2ShortSortedMap headMap(int to) { return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync); }
/* 249:249 */    public Int2ShortSortedMap tailMap(int from) { return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync); }
/* 250:    */    
/* 251:251 */    public int firstIntKey() { synchronized (this.sync) { return this.sortedMap.firstIntKey(); } }
/* 252:252 */    public int lastIntKey() { synchronized (this.sync) { return this.sortedMap.lastIntKey();
/* 253:    */      } }
/* 254:    */    
/* 255:255 */    public Integer firstKey() { synchronized (this.sync) { return (Integer)this.sortedMap.firstKey(); } }
/* 256:256 */    public Integer lastKey() { synchronized (this.sync) { return (Integer)this.sortedMap.lastKey(); } }
/* 257:    */    
/* 258:258 */    public Int2ShortSortedMap subMap(Integer from, Integer to) { return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync); }
/* 259:259 */    public Int2ShortSortedMap headMap(Integer to) { return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync); }
/* 260:260 */    public Int2ShortSortedMap tailMap(Integer from) { return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync); }
/* 261:    */  }
/* 262:    */  
/* 270:    */  public static Int2ShortSortedMap synchronize(Int2ShortSortedMap m)
/* 271:    */  {
/* 272:272 */    return new SynchronizedSortedMap(m);
/* 273:    */  }
/* 274:    */  
/* 280:    */  public static Int2ShortSortedMap synchronize(Int2ShortSortedMap m, Object sync)
/* 281:    */  {
/* 282:282 */    return new SynchronizedSortedMap(m, sync);
/* 283:    */  }
/* 284:    */  
/* 286:    */  public static class UnmodifiableSortedMap
/* 287:    */    extends Int2ShortMaps.UnmodifiableMap
/* 288:    */    implements Int2ShortSortedMap, Serializable
/* 289:    */  {
/* 290:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 291:    */    
/* 292:    */    protected final Int2ShortSortedMap sortedMap;
/* 293:    */    
/* 294:    */    protected UnmodifiableSortedMap(Int2ShortSortedMap m)
/* 295:    */    {
/* 296:296 */      super();
/* 297:297 */      this.sortedMap = m;
/* 298:    */    }
/* 299:    */    
/* 300:300 */    public IntComparator comparator() { return this.sortedMap.comparator(); }
/* 301:    */    
/* 302:302 */    public ObjectSortedSet<Int2ShortMap.Entry> int2ShortEntrySet() { if (this.entries == null) this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.int2ShortEntrySet()); return (ObjectSortedSet)this.entries; }
/* 303:    */    
/* 304:304 */    public ObjectSortedSet<Map.Entry<Integer, Short>> entrySet() { return int2ShortEntrySet(); }
/* 305:305 */    public IntSortedSet keySet() { if (this.keys == null) this.keys = IntSortedSets.unmodifiable(this.sortedMap.keySet()); return (IntSortedSet)this.keys; }
/* 306:    */    
/* 307:307 */    public Int2ShortSortedMap subMap(int from, int to) { return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to)); }
/* 308:308 */    public Int2ShortSortedMap headMap(int to) { return new UnmodifiableSortedMap(this.sortedMap.headMap(to)); }
/* 309:309 */    public Int2ShortSortedMap tailMap(int from) { return new UnmodifiableSortedMap(this.sortedMap.tailMap(from)); }
/* 310:    */    
/* 311:311 */    public int firstIntKey() { return this.sortedMap.firstIntKey(); }
/* 312:312 */    public int lastIntKey() { return this.sortedMap.lastIntKey(); }
/* 313:    */    
/* 315:315 */    public Integer firstKey() { return (Integer)this.sortedMap.firstKey(); }
/* 316:316 */    public Integer lastKey() { return (Integer)this.sortedMap.lastKey(); }
/* 317:    */    
/* 318:318 */    public Int2ShortSortedMap subMap(Integer from, Integer to) { return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to)); }
/* 319:319 */    public Int2ShortSortedMap headMap(Integer to) { return new UnmodifiableSortedMap(this.sortedMap.headMap(to)); }
/* 320:320 */    public Int2ShortSortedMap tailMap(Integer from) { return new UnmodifiableSortedMap(this.sortedMap.tailMap(from)); }
/* 321:    */  }
/* 322:    */  
/* 330:    */  public static Int2ShortSortedMap unmodifiable(Int2ShortSortedMap m)
/* 331:    */  {
/* 332:332 */    return new UnmodifiableSortedMap(m);
/* 333:    */  }
/* 334:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.ints.Int2ShortSortedMaps
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */