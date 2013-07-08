/*   1:    */package it.unimi.dsi.fastutil.objects;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.Hash;
/*   4:    */import it.unimi.dsi.fastutil.Hash.Strategy;
/*   5:    */import it.unimi.dsi.fastutil.HashCommon;
/*   6:    */import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*   7:    */import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*   8:    */import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*   9:    */import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*  10:    */import java.io.IOException;
/*  11:    */import java.io.ObjectInputStream;
/*  12:    */import java.io.ObjectOutputStream;
/*  13:    */import java.io.Serializable;
/*  14:    */import java.util.Map;
/*  15:    */import java.util.Map.Entry;
/*  16:    */import java.util.NoSuchElementException;
/*  17:    */
/*  89:    */public class Object2BooleanOpenCustomHashMap<K>
/*  90:    */  extends AbstractObject2BooleanMap<K>
/*  91:    */  implements Serializable, Cloneable, Hash
/*  92:    */{
/*  93:    */  public static final long serialVersionUID = 0L;
/*  94:    */  private static final boolean ASSERTS = false;
/*  95:    */  protected transient K[] key;
/*  96:    */  protected transient boolean[] value;
/*  97:    */  protected transient boolean[] used;
/*  98:    */  protected final float f;
/*  99:    */  protected transient int n;
/* 100:    */  protected transient int maxFill;
/* 101:    */  protected transient int mask;
/* 102:    */  protected int size;
/* 103:    */  protected volatile transient Object2BooleanMap.FastEntrySet<K> entries;
/* 104:    */  protected volatile transient ObjectSet<K> keys;
/* 105:    */  protected volatile transient BooleanCollection values;
/* 106:    */  protected Hash.Strategy<K> strategy;
/* 107:    */  
/* 108:    */  public Object2BooleanOpenCustomHashMap(int expected, float f, Hash.Strategy<K> strategy)
/* 109:    */  {
/* 110:110 */    this.strategy = strategy;
/* 111:111 */    if ((f <= 0.0F) || (f > 1.0F)) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
/* 112:112 */    if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative");
/* 113:113 */    this.f = f;
/* 114:114 */    this.n = HashCommon.arraySize(expected, f);
/* 115:115 */    this.mask = (this.n - 1);
/* 116:116 */    this.maxFill = HashCommon.maxFill(this.n, f);
/* 117:117 */    this.key = ((Object[])new Object[this.n]);
/* 118:118 */    this.value = new boolean[this.n];
/* 119:119 */    this.used = new boolean[this.n];
/* 120:    */  }
/* 121:    */  
/* 125:    */  public Object2BooleanOpenCustomHashMap(int expected, Hash.Strategy<K> strategy)
/* 126:    */  {
/* 127:127 */    this(expected, 0.75F, strategy);
/* 128:    */  }
/* 129:    */  
/* 132:    */  public Object2BooleanOpenCustomHashMap(Hash.Strategy<K> strategy)
/* 133:    */  {
/* 134:134 */    this(16, 0.75F, strategy);
/* 135:    */  }
/* 136:    */  
/* 141:    */  public Object2BooleanOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, float f, Hash.Strategy<K> strategy)
/* 142:    */  {
/* 143:143 */    this(m.size(), f, strategy);
/* 144:144 */    putAll(m);
/* 145:    */  }
/* 146:    */  
/* 150:    */  public Object2BooleanOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, Hash.Strategy<K> strategy)
/* 151:    */  {
/* 152:152 */    this(m, 0.75F, strategy);
/* 153:    */  }
/* 154:    */  
/* 159:    */  public Object2BooleanOpenCustomHashMap(Object2BooleanMap<K> m, float f, Hash.Strategy<K> strategy)
/* 160:    */  {
/* 161:161 */    this(m.size(), f, strategy);
/* 162:162 */    putAll(m);
/* 163:    */  }
/* 164:    */  
/* 168:    */  public Object2BooleanOpenCustomHashMap(Object2BooleanMap<K> m, Hash.Strategy<K> strategy)
/* 169:    */  {
/* 170:170 */    this(m, 0.75F, strategy);
/* 171:    */  }
/* 172:    */  
/* 179:    */  public Object2BooleanOpenCustomHashMap(K[] k, boolean[] v, float f, Hash.Strategy<K> strategy)
/* 180:    */  {
/* 181:181 */    this(k.length, f, strategy);
/* 182:182 */    if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/* 183:183 */    for (int i = 0; i < k.length; i++) { put(k[i], v[i]);
/* 184:    */    }
/* 185:    */  }
/* 186:    */  
/* 191:    */  public Object2BooleanOpenCustomHashMap(K[] k, boolean[] v, Hash.Strategy<K> strategy)
/* 192:    */  {
/* 193:193 */    this(k, v, 0.75F, strategy);
/* 194:    */  }
/* 195:    */  
/* 198:    */  public Hash.Strategy<K> strategy()
/* 199:    */  {
/* 200:200 */    return this.strategy;
/* 201:    */  }
/* 202:    */  
/* 206:    */  public boolean put(K k, boolean v)
/* 207:    */  {
/* 208:208 */    int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/* 209:    */    
/* 210:210 */    while (this.used[pos] != 0) {
/* 211:211 */      if (this.strategy.equals(this.key[pos], k)) {
/* 212:212 */        boolean oldValue = this.value[pos];
/* 213:213 */        this.value[pos] = v;
/* 214:214 */        return oldValue;
/* 215:    */      }
/* 216:216 */      pos = pos + 1 & this.mask;
/* 217:    */    }
/* 218:218 */    this.used[pos] = true;
/* 219:219 */    this.key[pos] = k;
/* 220:220 */    this.value[pos] = v;
/* 221:221 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size + 1, this.f));
/* 222:    */    }
/* 223:223 */    return this.defRetValue;
/* 224:    */  }
/* 225:    */  
/* 226:226 */  public Boolean put(K ok, Boolean ov) { boolean v = ov.booleanValue();
/* 227:227 */    K k = ok;
/* 228:    */    
/* 229:229 */    int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/* 230:    */    
/* 231:231 */    while (this.used[pos] != 0) {
/* 232:232 */      if (this.strategy.equals(this.key[pos], k)) {
/* 233:233 */        Boolean oldValue = Boolean.valueOf(this.value[pos]);
/* 234:234 */        this.value[pos] = v;
/* 235:235 */        return oldValue;
/* 236:    */      }
/* 237:237 */      pos = pos + 1 & this.mask;
/* 238:    */    }
/* 239:239 */    this.used[pos] = true;
/* 240:240 */    this.key[pos] = k;
/* 241:241 */    this.value[pos] = v;
/* 242:242 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size + 1, this.f));
/* 243:    */    }
/* 244:244 */    return null;
/* 245:    */  }
/* 246:    */  
/* 249:    */  protected final int shiftKeys(int pos)
/* 250:    */  {
/* 251:    */    int last;
/* 252:    */    
/* 254:    */    for (;;)
/* 255:    */    {
/* 256:256 */      pos = (last = pos) + 1 & this.mask;
/* 257:257 */      while (this.used[pos] != 0) {
/* 258:258 */        int slot = HashCommon.murmurHash3(this.strategy.hashCode(this.key[pos])) & this.mask;
/* 259:259 */        if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/* 260:260 */        pos = pos + 1 & this.mask;
/* 261:    */      }
/* 262:262 */      if (this.used[pos] == 0) break;
/* 263:263 */      this.key[last] = this.key[pos];
/* 264:264 */      this.value[last] = this.value[pos];
/* 265:    */    }
/* 266:266 */    this.used[last] = false;
/* 267:267 */    this.key[last] = null;
/* 268:268 */    return last;
/* 269:    */  }
/* 270:    */  
/* 271:    */  public boolean removeBoolean(Object k)
/* 272:    */  {
/* 273:273 */    int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/* 274:    */    
/* 275:275 */    while (this.used[pos] != 0) {
/* 276:276 */      if (this.strategy.equals(this.key[pos], k)) {
/* 277:277 */        this.size -= 1;
/* 278:278 */        boolean v = this.value[pos];
/* 279:279 */        shiftKeys(pos);
/* 280:280 */        return v;
/* 281:    */      }
/* 282:282 */      pos = pos + 1 & this.mask;
/* 283:    */    }
/* 284:284 */    return this.defRetValue;
/* 285:    */  }
/* 286:    */  
/* 287:    */  public Boolean remove(Object ok) {
/* 288:288 */    K k = ok;
/* 289:    */    
/* 290:290 */    int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/* 291:    */    
/* 292:292 */    while (this.used[pos] != 0) {
/* 293:293 */      if (this.strategy.equals(this.key[pos], k)) {
/* 294:294 */        this.size -= 1;
/* 295:295 */        boolean v = this.value[pos];
/* 296:296 */        shiftKeys(pos);
/* 297:297 */        return Boolean.valueOf(v);
/* 298:    */      }
/* 299:299 */      pos = pos + 1 & this.mask;
/* 300:    */    }
/* 301:301 */    return null;
/* 302:    */  }
/* 303:    */  
/* 304:    */  public boolean getBoolean(Object k)
/* 305:    */  {
/* 306:306 */    int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/* 307:    */    
/* 308:308 */    while (this.used[pos] != 0) {
/* 309:309 */      if (this.strategy.equals(this.key[pos], k)) return this.value[pos];
/* 310:310 */      pos = pos + 1 & this.mask;
/* 311:    */    }
/* 312:312 */    return this.defRetValue;
/* 313:    */  }
/* 314:    */  
/* 315:    */  public boolean containsKey(Object k)
/* 316:    */  {
/* 317:317 */    int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/* 318:    */    
/* 319:319 */    while (this.used[pos] != 0) {
/* 320:320 */      if (this.strategy.equals(this.key[pos], k)) return true;
/* 321:321 */      pos = pos + 1 & this.mask;
/* 322:    */    }
/* 323:323 */    return false;
/* 324:    */  }
/* 325:    */  
/* 326:326 */  public boolean containsValue(boolean v) { boolean[] value = this.value;
/* 327:327 */    boolean[] used = this.used;
/* 328:328 */    for (int i = this.n; i-- != 0; return true) label16: if ((used[i] == 0) || (value[i] != v)) break label16;
/* 329:329 */    return false;
/* 330:    */  }
/* 331:    */  
/* 336:    */  public void clear()
/* 337:    */  {
/* 338:338 */    if (this.size == 0) return;
/* 339:339 */    this.size = 0;
/* 340:340 */    BooleanArrays.fill(this.used, false);
/* 341:    */    
/* 342:342 */    ObjectArrays.fill(this.key, null);
/* 343:    */  }
/* 344:    */  
/* 345:345 */  public int size() { return this.size; }
/* 346:    */  
/* 347:    */  public boolean isEmpty() {
/* 348:348 */    return this.size == 0;
/* 349:    */  }
/* 350:    */  
/* 355:    */  @Deprecated
/* 356:    */  public void growthFactor(int growthFactor) {}
/* 357:    */  
/* 362:    */  @Deprecated
/* 363:    */  public int growthFactor()
/* 364:    */  {
/* 365:365 */    return 16;
/* 366:    */  }
/* 367:    */  
/* 368:    */  private final class MapEntry
/* 369:    */    implements Object2BooleanMap.Entry<K>, Map.Entry<K, Boolean>
/* 370:    */  {
/* 371:    */    private int index;
/* 372:    */    
/* 373:    */    MapEntry(int index)
/* 374:    */    {
/* 375:375 */      this.index = index;
/* 376:    */    }
/* 377:    */    
/* 378:378 */    public K getKey() { return Object2BooleanOpenCustomHashMap.this.key[this.index]; }
/* 379:    */    
/* 380:    */    public Boolean getValue() {
/* 381:381 */      return Boolean.valueOf(Object2BooleanOpenCustomHashMap.this.value[this.index]);
/* 382:    */    }
/* 383:    */    
/* 384:384 */    public boolean getBooleanValue() { return Object2BooleanOpenCustomHashMap.this.value[this.index]; }
/* 385:    */    
/* 386:    */    public boolean setValue(boolean v) {
/* 387:387 */      boolean oldValue = Object2BooleanOpenCustomHashMap.this.value[this.index];
/* 388:388 */      Object2BooleanOpenCustomHashMap.this.value[this.index] = v;
/* 389:389 */      return oldValue;
/* 390:    */    }
/* 391:    */    
/* 392:392 */    public Boolean setValue(Boolean v) { return Boolean.valueOf(setValue(v.booleanValue())); }
/* 393:    */    
/* 394:    */    public boolean equals(Object o)
/* 395:    */    {
/* 396:396 */      if (!(o instanceof Map.Entry)) return false;
/* 397:397 */      Map.Entry<K, Boolean> e = (Map.Entry)o;
/* 398:398 */      return (Object2BooleanOpenCustomHashMap.this.strategy.equals(Object2BooleanOpenCustomHashMap.this.key[this.index], e.getKey())) && (Object2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/* 399:    */    }
/* 400:    */    
/* 401:401 */    public int hashCode() { return Object2BooleanOpenCustomHashMap.this.strategy.hashCode(Object2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Object2BooleanOpenCustomHashMap.this.value[this.index] != 0 ? 1231 : 1237); }
/* 402:    */    
/* 404:404 */    public String toString() { return Object2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Object2BooleanOpenCustomHashMap.this.value[this.index]; } }
/* 405:    */  
/* 406:    */  private class MapIterator { int pos;
/* 407:    */    int last;
/* 408:    */    int c;
/* 409:    */    ObjectArrayList<K> wrapped;
/* 410:    */    
/* 411:411 */    private MapIterator() { this.pos = Object2BooleanOpenCustomHashMap.this.n;
/* 412:    */      
/* 414:414 */      this.last = -1;
/* 415:    */      
/* 416:416 */      this.c = Object2BooleanOpenCustomHashMap.this.size;
/* 417:    */      
/* 421:421 */      boolean[] used = Object2BooleanOpenCustomHashMap.this.used;
/* 422:422 */      while ((this.c != 0) && (used[(--this.pos)] == 0)) {}
/* 423:    */    }
/* 424:    */    
/* 425:425 */    public boolean hasNext() { return this.c != 0; }
/* 426:    */    
/* 427:    */    public int nextEntry() {
/* 428:428 */      if (!hasNext()) throw new NoSuchElementException();
/* 429:429 */      this.c -= 1;
/* 430:    */      
/* 431:431 */      if (this.pos < 0) {
/* 432:432 */        Object k = this.wrapped.get(-(this.last = --this.pos) - 2);
/* 433:    */        
/* 434:434 */        int pos = HashCommon.murmurHash3(Object2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanOpenCustomHashMap.this.mask;
/* 435:    */        
/* 436:436 */        while (Object2BooleanOpenCustomHashMap.this.used[pos] != 0) {
/* 437:437 */          if (Object2BooleanOpenCustomHashMap.this.strategy.equals(Object2BooleanOpenCustomHashMap.this.key[pos], k)) return pos;
/* 438:438 */          pos = pos + 1 & Object2BooleanOpenCustomHashMap.this.mask;
/* 439:    */        }
/* 440:    */      }
/* 441:441 */      this.last = this.pos;
/* 442:    */      
/* 443:443 */      if (this.c != 0) {
/* 444:444 */        boolean[] used = Object2BooleanOpenCustomHashMap.this.used;
/* 445:445 */        while ((this.pos-- != 0) && (used[this.pos] == 0)) {}
/* 446:    */      }
/* 447:    */      
/* 448:448 */      return this.last;
/* 449:    */    }
/* 450:    */    
/* 454:    */    protected final int shiftKeys(int pos)
/* 455:    */    {
/* 456:    */      int last;
/* 457:    */      
/* 459:    */      for (;;)
/* 460:    */      {
/* 461:461 */        pos = (last = pos) + 1 & Object2BooleanOpenCustomHashMap.this.mask;
/* 462:462 */        while (Object2BooleanOpenCustomHashMap.this.used[pos] != 0) {
/* 463:463 */          int slot = HashCommon.murmurHash3(Object2BooleanOpenCustomHashMap.this.strategy.hashCode(Object2BooleanOpenCustomHashMap.this.key[pos])) & Object2BooleanOpenCustomHashMap.this.mask;
/* 464:464 */          if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/* 465:465 */          pos = pos + 1 & Object2BooleanOpenCustomHashMap.this.mask;
/* 466:    */        }
/* 467:467 */        if (Object2BooleanOpenCustomHashMap.this.used[pos] == 0) break;
/* 468:468 */        if (pos < last)
/* 469:    */        {
/* 470:470 */          if (this.wrapped == null) this.wrapped = new ObjectArrayList();
/* 471:471 */          this.wrapped.add(Object2BooleanOpenCustomHashMap.this.key[pos]);
/* 472:    */        }
/* 473:473 */        Object2BooleanOpenCustomHashMap.this.key[last] = Object2BooleanOpenCustomHashMap.this.key[pos];
/* 474:474 */        Object2BooleanOpenCustomHashMap.this.value[last] = Object2BooleanOpenCustomHashMap.this.value[pos];
/* 475:    */      }
/* 476:476 */      Object2BooleanOpenCustomHashMap.this.used[last] = false;
/* 477:477 */      Object2BooleanOpenCustomHashMap.this.key[last] = null;
/* 478:478 */      return last;
/* 479:    */    }
/* 480:    */    
/* 481:    */    public void remove() {
/* 482:482 */      if (this.last == -1) throw new IllegalStateException();
/* 483:483 */      if (this.pos < -1)
/* 484:    */      {
/* 485:485 */        Object2BooleanOpenCustomHashMap.this.remove(this.wrapped.set(-this.pos - 2, null));
/* 486:486 */        this.last = -1;
/* 487:487 */        return;
/* 488:    */      }
/* 489:489 */      Object2BooleanOpenCustomHashMap.this.size -= 1;
/* 490:490 */      if ((shiftKeys(this.last) == this.pos) && (this.c > 0)) {
/* 491:491 */        this.c += 1;
/* 492:492 */        nextEntry();
/* 493:    */      }
/* 494:494 */      this.last = -1;
/* 495:    */    }
/* 496:    */    
/* 497:    */    public int skip(int n) {
/* 498:498 */      int i = n;
/* 499:499 */      while ((i-- != 0) && (hasNext())) nextEntry();
/* 500:500 */      return n - i - 1;
/* 501:    */    } }
/* 502:    */  
/* 503:503 */  private class EntryIterator extends Object2BooleanOpenCustomHashMap<K>.MapIterator implements ObjectIterator<Object2BooleanMap.Entry<K>> { private EntryIterator() { super(null); }
/* 504:    */    
/* 505:    */    private Object2BooleanOpenCustomHashMap<K>.MapEntry entry;
/* 506:506 */    public Object2BooleanMap.Entry<K> next() { return this.entry = new Object2BooleanOpenCustomHashMap.MapEntry(Object2BooleanOpenCustomHashMap.this, nextEntry()); }
/* 507:    */    
/* 508:    */    public void remove()
/* 509:    */    {
/* 510:510 */      super.remove();
/* 511:511 */      Object2BooleanOpenCustomHashMap.MapEntry.access$102(this.entry, -1);
/* 512:    */    } }
/* 513:    */  
/* 514:514 */  private class FastEntryIterator extends Object2BooleanOpenCustomHashMap<K>.MapIterator implements ObjectIterator<Object2BooleanMap.Entry<K>> { private FastEntryIterator() { super(null); }
/* 515:515 */    final AbstractObject2BooleanMap.BasicEntry<K> entry = new AbstractObject2BooleanMap.BasicEntry(null, false);
/* 516:    */    
/* 517:517 */    public AbstractObject2BooleanMap.BasicEntry<K> next() { int e = nextEntry();
/* 518:518 */      this.entry.key = Object2BooleanOpenCustomHashMap.this.key[e];
/* 519:519 */      this.entry.value = Object2BooleanOpenCustomHashMap.this.value[e];
/* 520:520 */      return this.entry;
/* 521:    */    } }
/* 522:    */  
/* 523:    */  private final class MapEntrySet extends AbstractObjectSet<Object2BooleanMap.Entry<K>> implements Object2BooleanMap.FastEntrySet<K> { private MapEntrySet() {}
/* 524:    */    
/* 525:525 */    public ObjectIterator<Object2BooleanMap.Entry<K>> iterator() { return new Object2BooleanOpenCustomHashMap.EntryIterator(Object2BooleanOpenCustomHashMap.this, null); }
/* 526:    */    
/* 527:    */    public ObjectIterator<Object2BooleanMap.Entry<K>> fastIterator() {
/* 528:528 */      return new Object2BooleanOpenCustomHashMap.FastEntryIterator(Object2BooleanOpenCustomHashMap.this, null);
/* 529:    */    }
/* 530:    */    
/* 531:    */    public boolean contains(Object o) {
/* 532:532 */      if (!(o instanceof Map.Entry)) return false;
/* 533:533 */      Map.Entry<K, Boolean> e = (Map.Entry)o;
/* 534:534 */      K k = e.getKey();
/* 535:    */      
/* 536:536 */      int pos = HashCommon.murmurHash3(Object2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanOpenCustomHashMap.this.mask;
/* 537:    */      
/* 538:538 */      while (Object2BooleanOpenCustomHashMap.this.used[pos] != 0) {
/* 539:539 */        if (Object2BooleanOpenCustomHashMap.this.strategy.equals(Object2BooleanOpenCustomHashMap.this.key[pos], k)) return Object2BooleanOpenCustomHashMap.this.value[pos] == ((Boolean)e.getValue()).booleanValue();
/* 540:540 */        pos = pos + 1 & Object2BooleanOpenCustomHashMap.this.mask;
/* 541:    */      }
/* 542:542 */      return false;
/* 543:    */    }
/* 544:    */    
/* 545:    */    public boolean remove(Object o) {
/* 546:546 */      if (!(o instanceof Map.Entry)) return false;
/* 547:547 */      Map.Entry<K, Boolean> e = (Map.Entry)o;
/* 548:548 */      K k = e.getKey();
/* 549:    */      
/* 550:550 */      int pos = HashCommon.murmurHash3(Object2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanOpenCustomHashMap.this.mask;
/* 551:    */      
/* 552:552 */      while (Object2BooleanOpenCustomHashMap.this.used[pos] != 0) {
/* 553:553 */        if (Object2BooleanOpenCustomHashMap.this.strategy.equals(Object2BooleanOpenCustomHashMap.this.key[pos], k)) {
/* 554:554 */          Object2BooleanOpenCustomHashMap.this.remove(e.getKey());
/* 555:555 */          return true;
/* 556:    */        }
/* 557:557 */        pos = pos + 1 & Object2BooleanOpenCustomHashMap.this.mask;
/* 558:    */      }
/* 559:559 */      return false;
/* 560:    */    }
/* 561:    */    
/* 562:562 */    public int size() { return Object2BooleanOpenCustomHashMap.this.size; }
/* 563:    */    
/* 565:565 */    public void clear() { Object2BooleanOpenCustomHashMap.this.clear(); }
/* 566:    */  }
/* 567:    */  
/* 568:    */  public Object2BooleanMap.FastEntrySet<K> object2BooleanEntrySet() {
/* 569:569 */    if (this.entries == null) this.entries = new MapEntrySet(null);
/* 570:570 */    return this.entries;
/* 571:    */  }
/* 572:    */  
/* 575:    */  private final class KeyIterator
/* 576:    */    extends Object2BooleanOpenCustomHashMap<K>.MapIterator
/* 577:    */    implements ObjectIterator<K>
/* 578:    */  {
/* 579:579 */    public KeyIterator() { super(null); }
/* 580:580 */    public K next() { return Object2BooleanOpenCustomHashMap.this.key[nextEntry()]; } }
/* 581:    */  
/* 582:    */  private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/* 583:    */    
/* 584:584 */    public ObjectIterator<K> iterator() { return new Object2BooleanOpenCustomHashMap.KeyIterator(Object2BooleanOpenCustomHashMap.this); }
/* 585:    */    
/* 586:    */    public int size() {
/* 587:587 */      return Object2BooleanOpenCustomHashMap.this.size;
/* 588:    */    }
/* 589:    */    
/* 590:590 */    public boolean contains(Object k) { return Object2BooleanOpenCustomHashMap.this.containsKey(k); }
/* 591:    */    
/* 592:    */    public boolean remove(Object k) {
/* 593:593 */      int oldSize = Object2BooleanOpenCustomHashMap.this.size;
/* 594:594 */      Object2BooleanOpenCustomHashMap.this.remove(k);
/* 595:595 */      return Object2BooleanOpenCustomHashMap.this.size != oldSize;
/* 596:    */    }
/* 597:    */    
/* 598:598 */    public void clear() { Object2BooleanOpenCustomHashMap.this.clear(); }
/* 599:    */  }
/* 600:    */  
/* 601:    */  public ObjectSet<K> keySet() {
/* 602:602 */    if (this.keys == null) this.keys = new KeySet(null);
/* 603:603 */    return this.keys;
/* 604:    */  }
/* 605:    */  
/* 608:    */  private final class ValueIterator
/* 609:    */    extends Object2BooleanOpenCustomHashMap.MapIterator
/* 610:    */    implements BooleanIterator
/* 611:    */  {
/* 612:612 */    public ValueIterator() { super(null); }
/* 613:613 */    public boolean nextBoolean() { return Object2BooleanOpenCustomHashMap.this.value[nextEntry()]; }
/* 614:614 */    public Boolean next() { return Boolean.valueOf(Object2BooleanOpenCustomHashMap.this.value[nextEntry()]); }
/* 615:    */  }
/* 616:    */  
/* 617:617 */  public BooleanCollection values() { if (this.values == null) { this.values = new AbstractBooleanCollection() {
/* 618:    */        public BooleanIterator iterator() {
/* 619:619 */          return new Object2BooleanOpenCustomHashMap.ValueIterator(Object2BooleanOpenCustomHashMap.this);
/* 620:    */        }
/* 621:    */        
/* 622:622 */        public int size() { return Object2BooleanOpenCustomHashMap.this.size; }
/* 623:    */        
/* 624:    */        public boolean contains(boolean v) {
/* 625:625 */          return Object2BooleanOpenCustomHashMap.this.containsValue(v);
/* 626:    */        }
/* 627:    */        
/* 628:628 */        public void clear() { Object2BooleanOpenCustomHashMap.this.clear(); }
/* 629:    */      };
/* 630:    */    }
/* 631:631 */    return this.values;
/* 632:    */  }
/* 633:    */  
/* 642:    */  @Deprecated
/* 643:    */  public boolean rehash()
/* 644:    */  {
/* 645:645 */    return true;
/* 646:    */  }
/* 647:    */  
/* 658:    */  public boolean trim()
/* 659:    */  {
/* 660:660 */    int l = HashCommon.arraySize(this.size, this.f);
/* 661:661 */    if (l >= this.n) return true;
/* 662:    */    try {
/* 663:663 */      rehash(l);
/* 664:    */    } catch (OutOfMemoryError cantDoIt) {
/* 665:665 */      return false; }
/* 666:666 */    return true;
/* 667:    */  }
/* 668:    */  
/* 685:    */  public boolean trim(int n)
/* 686:    */  {
/* 687:687 */    int l = HashCommon.nextPowerOfTwo((int)Math.ceil(n / this.f));
/* 688:688 */    if (this.n <= l) return true;
/* 689:    */    try {
/* 690:690 */      rehash(l);
/* 691:    */    } catch (OutOfMemoryError cantDoIt) {
/* 692:692 */      return false; }
/* 693:693 */    return true;
/* 694:    */  }
/* 695:    */  
/* 704:    */  protected void rehash(int newN)
/* 705:    */  {
/* 706:706 */    int i = 0;
/* 707:707 */    boolean[] used = this.used;
/* 708:    */    
/* 709:709 */    K[] key = this.key;
/* 710:710 */    boolean[] value = this.value;
/* 711:711 */    int newMask = newN - 1;
/* 712:712 */    K[] newKey = (Object[])new Object[newN];
/* 713:713 */    boolean[] newValue = new boolean[newN];
/* 714:714 */    boolean[] newUsed = new boolean[newN];
/* 715:715 */    for (int j = this.size; j-- != 0;) {
/* 716:716 */      while (used[i] == 0) i++;
/* 717:717 */      K k = key[i];
/* 718:718 */      int pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & newMask;
/* 719:719 */      while (newUsed[pos] != 0) pos = pos + 1 & newMask;
/* 720:720 */      newUsed[pos] = true;
/* 721:721 */      newKey[pos] = k;
/* 722:722 */      newValue[pos] = value[i];
/* 723:723 */      i++;
/* 724:    */    }
/* 725:725 */    this.n = newN;
/* 726:726 */    this.mask = newMask;
/* 727:727 */    this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 728:728 */    this.key = newKey;
/* 729:729 */    this.value = newValue;
/* 730:730 */    this.used = newUsed;
/* 731:    */  }
/* 732:    */  
/* 736:    */  public Object2BooleanOpenCustomHashMap<K> clone()
/* 737:    */  {
/* 738:    */    Object2BooleanOpenCustomHashMap<K> c;
/* 739:    */    
/* 741:    */    try
/* 742:    */    {
/* 743:743 */      c = (Object2BooleanOpenCustomHashMap)super.clone();
/* 744:    */    }
/* 745:    */    catch (CloneNotSupportedException cantHappen) {
/* 746:746 */      throw new InternalError();
/* 747:    */    }
/* 748:748 */    c.keys = null;
/* 749:749 */    c.values = null;
/* 750:750 */    c.entries = null;
/* 751:751 */    c.key = ((Object[])this.key.clone());
/* 752:752 */    c.value = ((boolean[])this.value.clone());
/* 753:753 */    c.used = ((boolean[])this.used.clone());
/* 754:754 */    c.strategy = this.strategy;
/* 755:755 */    return c;
/* 756:    */  }
/* 757:    */  
/* 765:    */  public int hashCode()
/* 766:    */  {
/* 767:767 */    int h = 0;
/* 768:768 */    int j = this.size;int i = 0; for (int t = 0; j-- != 0;) {
/* 769:769 */      while (this.used[i] == 0) i++;
/* 770:770 */      if (this != this.key[i])
/* 771:771 */        t = this.strategy.hashCode(this.key[i]);
/* 772:772 */      t ^= (this.value[i] != 0 ? 1231 : 1237);
/* 773:773 */      h += t;
/* 774:774 */      i++;
/* 775:    */    }
/* 776:776 */    return h;
/* 777:    */  }
/* 778:    */  
/* 779:779 */  private void writeObject(ObjectOutputStream s) throws IOException { K[] key = this.key;
/* 780:780 */    boolean[] value = this.value;
/* 781:781 */    Object2BooleanOpenCustomHashMap<K>.MapIterator i = new MapIterator(null);
/* 782:782 */    s.defaultWriteObject();
/* 783:783 */    for (int j = this.size; j-- != 0;) {
/* 784:784 */      int e = i.nextEntry();
/* 785:785 */      s.writeObject(key[e]);
/* 786:786 */      s.writeBoolean(value[e]);
/* 787:    */    }
/* 788:    */  }
/* 789:    */  
/* 790:    */  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 791:791 */    s.defaultReadObject();
/* 792:792 */    this.n = HashCommon.arraySize(this.size, this.f);
/* 793:793 */    this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 794:794 */    this.mask = (this.n - 1);
/* 795:795 */    K[] key = this.key = (Object[])new Object[this.n];
/* 796:796 */    boolean[] value = this.value = new boolean[this.n];
/* 797:797 */    boolean[] used = this.used = new boolean[this.n];
/* 798:    */    
/* 800:800 */    int i = this.size; for (int pos = 0; i-- != 0;) {
/* 801:801 */      K k = s.readObject();
/* 802:802 */      boolean v = s.readBoolean();
/* 803:803 */      pos = HashCommon.murmurHash3(this.strategy.hashCode(k)) & this.mask;
/* 804:804 */      while (used[pos] != 0) pos = pos + 1 & this.mask;
/* 805:805 */      used[pos] = true;
/* 806:806 */      key[pos] = k;
/* 807:807 */      value[pos] = v;
/* 808:    */    }
/* 809:    */  }
/* 810:    */  
/* 811:    */  private void checkTable() {}
/* 812:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.Object2BooleanOpenCustomHashMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */