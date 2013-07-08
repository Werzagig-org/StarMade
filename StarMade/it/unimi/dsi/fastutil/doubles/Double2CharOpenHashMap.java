/*   1:    */package it.unimi.dsi.fastutil.doubles;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.Hash;
/*   4:    */import it.unimi.dsi.fastutil.HashCommon;
/*   5:    */import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*   6:    */import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*   7:    */import it.unimi.dsi.fastutil.chars.CharCollection;
/*   8:    */import it.unimi.dsi.fastutil.chars.CharIterator;
/*   9:    */import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*  10:    */import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*  11:    */import java.io.IOException;
/*  12:    */import java.io.ObjectInputStream;
/*  13:    */import java.io.ObjectOutputStream;
/*  14:    */import java.io.Serializable;
/*  15:    */import java.util.Map;
/*  16:    */import java.util.Map.Entry;
/*  17:    */import java.util.NoSuchElementException;
/*  18:    */
/*  87:    */public class Double2CharOpenHashMap
/*  88:    */  extends AbstractDouble2CharMap
/*  89:    */  implements Serializable, Cloneable, Hash
/*  90:    */{
/*  91:    */  public static final long serialVersionUID = 0L;
/*  92:    */  private static final boolean ASSERTS = false;
/*  93:    */  protected transient double[] key;
/*  94:    */  protected transient char[] value;
/*  95:    */  protected transient boolean[] used;
/*  96:    */  protected final float f;
/*  97:    */  protected transient int n;
/*  98:    */  protected transient int maxFill;
/*  99:    */  protected transient int mask;
/* 100:    */  protected int size;
/* 101:    */  protected volatile transient Double2CharMap.FastEntrySet entries;
/* 102:    */  protected volatile transient DoubleSet keys;
/* 103:    */  protected volatile transient CharCollection values;
/* 104:    */  
/* 105:    */  public Double2CharOpenHashMap(int expected, float f)
/* 106:    */  {
/* 107:107 */    if ((f <= 0.0F) || (f > 1.0F)) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
/* 108:108 */    if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative");
/* 109:109 */    this.f = f;
/* 110:110 */    this.n = HashCommon.arraySize(expected, f);
/* 111:111 */    this.mask = (this.n - 1);
/* 112:112 */    this.maxFill = HashCommon.maxFill(this.n, f);
/* 113:113 */    this.key = new double[this.n];
/* 114:114 */    this.value = new char[this.n];
/* 115:115 */    this.used = new boolean[this.n];
/* 116:    */  }
/* 117:    */  
/* 120:    */  public Double2CharOpenHashMap(int expected)
/* 121:    */  {
/* 122:122 */    this(expected, 0.75F);
/* 123:    */  }
/* 124:    */  
/* 126:    */  public Double2CharOpenHashMap()
/* 127:    */  {
/* 128:128 */    this(16, 0.75F);
/* 129:    */  }
/* 130:    */  
/* 134:    */  public Double2CharOpenHashMap(Map<? extends Double, ? extends Character> m, float f)
/* 135:    */  {
/* 136:136 */    this(m.size(), f);
/* 137:137 */    putAll(m);
/* 138:    */  }
/* 139:    */  
/* 142:    */  public Double2CharOpenHashMap(Map<? extends Double, ? extends Character> m)
/* 143:    */  {
/* 144:144 */    this(m, 0.75F);
/* 145:    */  }
/* 146:    */  
/* 150:    */  public Double2CharOpenHashMap(Double2CharMap m, float f)
/* 151:    */  {
/* 152:152 */    this(m.size(), f);
/* 153:153 */    putAll(m);
/* 154:    */  }
/* 155:    */  
/* 158:    */  public Double2CharOpenHashMap(Double2CharMap m)
/* 159:    */  {
/* 160:160 */    this(m, 0.75F);
/* 161:    */  }
/* 162:    */  
/* 168:    */  public Double2CharOpenHashMap(double[] k, char[] v, float f)
/* 169:    */  {
/* 170:170 */    this(k.length, f);
/* 171:171 */    if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/* 172:172 */    for (int i = 0; i < k.length; i++) { put(k[i], v[i]);
/* 173:    */    }
/* 174:    */  }
/* 175:    */  
/* 179:    */  public Double2CharOpenHashMap(double[] k, char[] v)
/* 180:    */  {
/* 181:181 */    this(k, v, 0.75F);
/* 182:    */  }
/* 183:    */  
/* 187:    */  public char put(double k, char v)
/* 188:    */  {
/* 189:189 */    int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 190:    */    
/* 191:191 */    while (this.used[pos] != 0) {
/* 192:192 */      if (this.key[pos] == k) {
/* 193:193 */        char oldValue = this.value[pos];
/* 194:194 */        this.value[pos] = v;
/* 195:195 */        return oldValue;
/* 196:    */      }
/* 197:197 */      pos = pos + 1 & this.mask;
/* 198:    */    }
/* 199:199 */    this.used[pos] = true;
/* 200:200 */    this.key[pos] = k;
/* 201:201 */    this.value[pos] = v;
/* 202:202 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size + 1, this.f));
/* 203:    */    }
/* 204:204 */    return this.defRetValue;
/* 205:    */  }
/* 206:    */  
/* 207:207 */  public Character put(Double ok, Character ov) { char v = ov.charValue();
/* 208:208 */    double k = ok.doubleValue();
/* 209:    */    
/* 210:210 */    int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 211:    */    
/* 212:212 */    while (this.used[pos] != 0) {
/* 213:213 */      if (this.key[pos] == k) {
/* 214:214 */        Character oldValue = Character.valueOf(this.value[pos]);
/* 215:215 */        this.value[pos] = v;
/* 216:216 */        return oldValue;
/* 217:    */      }
/* 218:218 */      pos = pos + 1 & this.mask;
/* 219:    */    }
/* 220:220 */    this.used[pos] = true;
/* 221:221 */    this.key[pos] = k;
/* 222:222 */    this.value[pos] = v;
/* 223:223 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size + 1, this.f));
/* 224:    */    }
/* 225:225 */    return null;
/* 226:    */  }
/* 227:    */  
/* 230:    */  protected final int shiftKeys(int pos)
/* 231:    */  {
/* 232:    */    int last;
/* 233:    */    
/* 235:    */    for (;;)
/* 236:    */    {
/* 237:237 */      pos = (last = pos) + 1 & this.mask;
/* 238:238 */      while (this.used[pos] != 0) {
/* 239:239 */        int slot = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(this.key[pos])) & this.mask;
/* 240:240 */        if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/* 241:241 */        pos = pos + 1 & this.mask;
/* 242:    */      }
/* 243:243 */      if (this.used[pos] == 0) break;
/* 244:244 */      this.key[last] = this.key[pos];
/* 245:245 */      this.value[last] = this.value[pos];
/* 246:    */    }
/* 247:247 */    this.used[last] = false;
/* 248:248 */    return last;
/* 249:    */  }
/* 250:    */  
/* 251:    */  public char remove(double k)
/* 252:    */  {
/* 253:253 */    int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 254:    */    
/* 255:255 */    while (this.used[pos] != 0) {
/* 256:256 */      if (this.key[pos] == k) {
/* 257:257 */        this.size -= 1;
/* 258:258 */        char v = this.value[pos];
/* 259:259 */        shiftKeys(pos);
/* 260:260 */        return v;
/* 261:    */      }
/* 262:262 */      pos = pos + 1 & this.mask;
/* 263:    */    }
/* 264:264 */    return this.defRetValue;
/* 265:    */  }
/* 266:    */  
/* 267:    */  public Character remove(Object ok) {
/* 268:268 */    double k = ((Double)ok).doubleValue();
/* 269:    */    
/* 270:270 */    int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 271:    */    
/* 272:272 */    while (this.used[pos] != 0) {
/* 273:273 */      if (this.key[pos] == k) {
/* 274:274 */        this.size -= 1;
/* 275:275 */        char v = this.value[pos];
/* 276:276 */        shiftKeys(pos);
/* 277:277 */        return Character.valueOf(v);
/* 278:    */      }
/* 279:279 */      pos = pos + 1 & this.mask;
/* 280:    */    }
/* 281:281 */    return null;
/* 282:    */  }
/* 283:    */  
/* 284:284 */  public Character get(Double ok) { double k = ok.doubleValue();
/* 285:    */    
/* 286:286 */    int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 287:    */    
/* 288:288 */    while (this.used[pos] != 0) {
/* 289:289 */      if (this.key[pos] == k) return Character.valueOf(this.value[pos]);
/* 290:290 */      pos = pos + 1 & this.mask;
/* 291:    */    }
/* 292:292 */    return null;
/* 293:    */  }
/* 294:    */  
/* 295:    */  public char get(double k)
/* 296:    */  {
/* 297:297 */    int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 298:    */    
/* 299:299 */    while (this.used[pos] != 0) {
/* 300:300 */      if (this.key[pos] == k) return this.value[pos];
/* 301:301 */      pos = pos + 1 & this.mask;
/* 302:    */    }
/* 303:303 */    return this.defRetValue;
/* 304:    */  }
/* 305:    */  
/* 306:    */  public boolean containsKey(double k)
/* 307:    */  {
/* 308:308 */    int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 309:    */    
/* 310:310 */    while (this.used[pos] != 0) {
/* 311:311 */      if (this.key[pos] == k) return true;
/* 312:312 */      pos = pos + 1 & this.mask;
/* 313:    */    }
/* 314:314 */    return false;
/* 315:    */  }
/* 316:    */  
/* 317:317 */  public boolean containsValue(char v) { char[] value = this.value;
/* 318:318 */    boolean[] used = this.used;
/* 319:319 */    for (int i = this.n; i-- != 0; return true) label16: if ((used[i] == 0) || (value[i] != v)) break label16;
/* 320:320 */    return false;
/* 321:    */  }
/* 322:    */  
/* 327:    */  public void clear()
/* 328:    */  {
/* 329:329 */    if (this.size == 0) return;
/* 330:330 */    this.size = 0;
/* 331:331 */    BooleanArrays.fill(this.used, false);
/* 332:    */  }
/* 333:    */  
/* 334:    */  public int size() {
/* 335:335 */    return this.size;
/* 336:    */  }
/* 337:    */  
/* 338:338 */  public boolean isEmpty() { return this.size == 0; }
/* 339:    */  
/* 345:    */  @Deprecated
/* 346:    */  public void growthFactor(int growthFactor) {}
/* 347:    */  
/* 352:    */  @Deprecated
/* 353:    */  public int growthFactor()
/* 354:    */  {
/* 355:355 */    return 16;
/* 356:    */  }
/* 357:    */  
/* 358:    */  private final class MapEntry
/* 359:    */    implements Double2CharMap.Entry, Map.Entry<Double, Character>
/* 360:    */  {
/* 361:    */    private int index;
/* 362:    */    
/* 363:    */    MapEntry(int index)
/* 364:    */    {
/* 365:365 */      this.index = index;
/* 366:    */    }
/* 367:    */    
/* 368:368 */    public Double getKey() { return Double.valueOf(Double2CharOpenHashMap.this.key[this.index]); }
/* 369:    */    
/* 370:    */    public double getDoubleKey() {
/* 371:371 */      return Double2CharOpenHashMap.this.key[this.index];
/* 372:    */    }
/* 373:    */    
/* 374:374 */    public Character getValue() { return Character.valueOf(Double2CharOpenHashMap.this.value[this.index]); }
/* 375:    */    
/* 377:377 */    public char getCharValue() { return Double2CharOpenHashMap.this.value[this.index]; }
/* 378:    */    
/* 379:    */    public char setValue(char v) {
/* 380:380 */      char oldValue = Double2CharOpenHashMap.this.value[this.index];
/* 381:381 */      Double2CharOpenHashMap.this.value[this.index] = v;
/* 382:382 */      return oldValue;
/* 383:    */    }
/* 384:    */    
/* 385:385 */    public Character setValue(Character v) { return Character.valueOf(setValue(v.charValue())); }
/* 386:    */    
/* 387:    */    public boolean equals(Object o)
/* 388:    */    {
/* 389:389 */      if (!(o instanceof Map.Entry)) return false;
/* 390:390 */      Map.Entry<Double, Character> e = (Map.Entry)o;
/* 391:391 */      return (Double2CharOpenHashMap.this.key[this.index] == ((Double)e.getKey()).doubleValue()) && (Double2CharOpenHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/* 392:    */    }
/* 393:    */    
/* 394:394 */    public int hashCode() { return HashCommon.double2int(Double2CharOpenHashMap.this.key[this.index]) ^ Double2CharOpenHashMap.this.value[this.index]; }
/* 395:    */    
/* 397:397 */    public String toString() { return Double2CharOpenHashMap.this.key[this.index] + "=>" + Double2CharOpenHashMap.this.value[this.index]; } }
/* 398:    */  
/* 399:    */  private class MapIterator { int pos;
/* 400:    */    int last;
/* 401:    */    int c;
/* 402:    */    DoubleArrayList wrapped;
/* 403:    */    
/* 404:404 */    private MapIterator() { this.pos = Double2CharOpenHashMap.this.n;
/* 405:    */      
/* 407:407 */      this.last = -1;
/* 408:    */      
/* 409:409 */      this.c = Double2CharOpenHashMap.this.size;
/* 410:    */      
/* 414:414 */      boolean[] used = Double2CharOpenHashMap.this.used;
/* 415:415 */      while ((this.c != 0) && (used[(--this.pos)] == 0)) {}
/* 416:    */    }
/* 417:    */    
/* 418:418 */    public boolean hasNext() { return this.c != 0; }
/* 419:    */    
/* 420:    */    public int nextEntry() {
/* 421:421 */      if (!hasNext()) throw new NoSuchElementException();
/* 422:422 */      this.c -= 1;
/* 423:    */      
/* 424:424 */      if (this.pos < 0) {
/* 425:425 */        double k = this.wrapped.getDouble(-(this.last = --this.pos) - 2);
/* 426:    */        
/* 427:427 */        int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & Double2CharOpenHashMap.this.mask;
/* 428:    */        
/* 429:429 */        while (Double2CharOpenHashMap.this.used[pos] != 0) {
/* 430:430 */          if (Double2CharOpenHashMap.this.key[pos] == k) return pos;
/* 431:431 */          pos = pos + 1 & Double2CharOpenHashMap.this.mask;
/* 432:    */        }
/* 433:    */      }
/* 434:434 */      this.last = this.pos;
/* 435:    */      
/* 436:436 */      if (this.c != 0) {
/* 437:437 */        boolean[] used = Double2CharOpenHashMap.this.used;
/* 438:438 */        while ((this.pos-- != 0) && (used[this.pos] == 0)) {}
/* 439:    */      }
/* 440:    */      
/* 441:441 */      return this.last;
/* 442:    */    }
/* 443:    */    
/* 447:    */    protected final int shiftKeys(int pos)
/* 448:    */    {
/* 449:    */      int last;
/* 450:    */      
/* 452:    */      for (;;)
/* 453:    */      {
/* 454:454 */        pos = (last = pos) + 1 & Double2CharOpenHashMap.this.mask;
/* 455:455 */        while (Double2CharOpenHashMap.this.used[pos] != 0) {
/* 456:456 */          int slot = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(Double2CharOpenHashMap.this.key[pos])) & Double2CharOpenHashMap.this.mask;
/* 457:457 */          if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/* 458:458 */          pos = pos + 1 & Double2CharOpenHashMap.this.mask;
/* 459:    */        }
/* 460:460 */        if (Double2CharOpenHashMap.this.used[pos] == 0) break;
/* 461:461 */        if (pos < last)
/* 462:    */        {
/* 463:463 */          if (this.wrapped == null) this.wrapped = new DoubleArrayList();
/* 464:464 */          this.wrapped.add(Double2CharOpenHashMap.this.key[pos]);
/* 465:    */        }
/* 466:466 */        Double2CharOpenHashMap.this.key[last] = Double2CharOpenHashMap.this.key[pos];
/* 467:467 */        Double2CharOpenHashMap.this.value[last] = Double2CharOpenHashMap.this.value[pos];
/* 468:    */      }
/* 469:469 */      Double2CharOpenHashMap.this.used[last] = false;
/* 470:470 */      return last;
/* 471:    */    }
/* 472:    */    
/* 473:    */    public void remove() {
/* 474:474 */      if (this.last == -1) throw new IllegalStateException();
/* 475:475 */      if (this.pos < -1)
/* 476:    */      {
/* 477:477 */        Double2CharOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 2));
/* 478:478 */        this.last = -1;
/* 479:479 */        return;
/* 480:    */      }
/* 481:481 */      Double2CharOpenHashMap.this.size -= 1;
/* 482:482 */      if ((shiftKeys(this.last) == this.pos) && (this.c > 0)) {
/* 483:483 */        this.c += 1;
/* 484:484 */        nextEntry();
/* 485:    */      }
/* 486:486 */      this.last = -1;
/* 487:    */    }
/* 488:    */    
/* 489:    */    public int skip(int n) {
/* 490:490 */      int i = n;
/* 491:491 */      while ((i-- != 0) && (hasNext())) nextEntry();
/* 492:492 */      return n - i - 1;
/* 493:    */    } }
/* 494:    */  
/* 495:495 */  private class EntryIterator extends Double2CharOpenHashMap.MapIterator implements ObjectIterator<Double2CharMap.Entry> { private EntryIterator() { super(null); }
/* 496:    */    
/* 497:    */    private Double2CharOpenHashMap.MapEntry entry;
/* 498:498 */    public Double2CharMap.Entry next() { return this.entry = new Double2CharOpenHashMap.MapEntry(Double2CharOpenHashMap.this, nextEntry()); }
/* 499:    */    
/* 500:    */    public void remove()
/* 501:    */    {
/* 502:502 */      super.remove();
/* 503:503 */      Double2CharOpenHashMap.MapEntry.access$102(this.entry, -1);
/* 504:    */    } }
/* 505:    */  
/* 506:506 */  private class FastEntryIterator extends Double2CharOpenHashMap.MapIterator implements ObjectIterator<Double2CharMap.Entry> { private FastEntryIterator() { super(null); }
/* 507:507 */    final AbstractDouble2CharMap.BasicEntry entry = new AbstractDouble2CharMap.BasicEntry(0.0D, '\000');
/* 508:    */    
/* 509:509 */    public AbstractDouble2CharMap.BasicEntry next() { int e = nextEntry();
/* 510:510 */      this.entry.key = Double2CharOpenHashMap.this.key[e];
/* 511:511 */      this.entry.value = Double2CharOpenHashMap.this.value[e];
/* 512:512 */      return this.entry;
/* 513:    */    } }
/* 514:    */  
/* 515:    */  private final class MapEntrySet extends AbstractObjectSet<Double2CharMap.Entry> implements Double2CharMap.FastEntrySet { private MapEntrySet() {}
/* 516:    */    
/* 517:517 */    public ObjectIterator<Double2CharMap.Entry> iterator() { return new Double2CharOpenHashMap.EntryIterator(Double2CharOpenHashMap.this, null); }
/* 518:    */    
/* 519:    */    public ObjectIterator<Double2CharMap.Entry> fastIterator() {
/* 520:520 */      return new Double2CharOpenHashMap.FastEntryIterator(Double2CharOpenHashMap.this, null);
/* 521:    */    }
/* 522:    */    
/* 523:    */    public boolean contains(Object o) {
/* 524:524 */      if (!(o instanceof Map.Entry)) return false;
/* 525:525 */      Map.Entry<Double, Character> e = (Map.Entry)o;
/* 526:526 */      double k = ((Double)e.getKey()).doubleValue();
/* 527:    */      
/* 528:528 */      int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & Double2CharOpenHashMap.this.mask;
/* 529:    */      
/* 530:530 */      while (Double2CharOpenHashMap.this.used[pos] != 0) {
/* 531:531 */        if (Double2CharOpenHashMap.this.key[pos] == k) return Double2CharOpenHashMap.this.value[pos] == ((Character)e.getValue()).charValue();
/* 532:532 */        pos = pos + 1 & Double2CharOpenHashMap.this.mask;
/* 533:    */      }
/* 534:534 */      return false;
/* 535:    */    }
/* 536:    */    
/* 537:    */    public boolean remove(Object o) {
/* 538:538 */      if (!(o instanceof Map.Entry)) return false;
/* 539:539 */      Map.Entry<Double, Character> e = (Map.Entry)o;
/* 540:540 */      double k = ((Double)e.getKey()).doubleValue();
/* 541:    */      
/* 542:542 */      int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & Double2CharOpenHashMap.this.mask;
/* 543:    */      
/* 544:544 */      while (Double2CharOpenHashMap.this.used[pos] != 0) {
/* 545:545 */        if (Double2CharOpenHashMap.this.key[pos] == k) {
/* 546:546 */          Double2CharOpenHashMap.this.remove(e.getKey());
/* 547:547 */          return true;
/* 548:    */        }
/* 549:549 */        pos = pos + 1 & Double2CharOpenHashMap.this.mask;
/* 550:    */      }
/* 551:551 */      return false;
/* 552:    */    }
/* 553:    */    
/* 554:554 */    public int size() { return Double2CharOpenHashMap.this.size; }
/* 555:    */    
/* 557:557 */    public void clear() { Double2CharOpenHashMap.this.clear(); }
/* 558:    */  }
/* 559:    */  
/* 560:    */  public Double2CharMap.FastEntrySet double2CharEntrySet() {
/* 561:561 */    if (this.entries == null) this.entries = new MapEntrySet(null);
/* 562:562 */    return this.entries;
/* 563:    */  }
/* 564:    */  
/* 567:    */  private final class KeyIterator
/* 568:    */    extends Double2CharOpenHashMap.MapIterator
/* 569:    */    implements DoubleIterator
/* 570:    */  {
/* 571:571 */    public KeyIterator() { super(null); }
/* 572:572 */    public double nextDouble() { return Double2CharOpenHashMap.this.key[nextEntry()]; }
/* 573:573 */    public Double next() { return Double.valueOf(Double2CharOpenHashMap.this.key[nextEntry()]); } }
/* 574:    */  
/* 575:    */  private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/* 576:    */    
/* 577:577 */    public DoubleIterator iterator() { return new Double2CharOpenHashMap.KeyIterator(Double2CharOpenHashMap.this); }
/* 578:    */    
/* 579:    */    public int size() {
/* 580:580 */      return Double2CharOpenHashMap.this.size;
/* 581:    */    }
/* 582:    */    
/* 583:583 */    public boolean contains(double k) { return Double2CharOpenHashMap.this.containsKey(k); }
/* 584:    */    
/* 585:    */    public boolean remove(double k) {
/* 586:586 */      int oldSize = Double2CharOpenHashMap.this.size;
/* 587:587 */      Double2CharOpenHashMap.this.remove(k);
/* 588:588 */      return Double2CharOpenHashMap.this.size != oldSize;
/* 589:    */    }
/* 590:    */    
/* 591:591 */    public void clear() { Double2CharOpenHashMap.this.clear(); }
/* 592:    */  }
/* 593:    */  
/* 594:    */  public DoubleSet keySet() {
/* 595:595 */    if (this.keys == null) this.keys = new KeySet(null);
/* 596:596 */    return this.keys;
/* 597:    */  }
/* 598:    */  
/* 601:    */  private final class ValueIterator
/* 602:    */    extends Double2CharOpenHashMap.MapIterator
/* 603:    */    implements CharIterator
/* 604:    */  {
/* 605:605 */    public ValueIterator() { super(null); }
/* 606:606 */    public char nextChar() { return Double2CharOpenHashMap.this.value[nextEntry()]; }
/* 607:607 */    public Character next() { return Character.valueOf(Double2CharOpenHashMap.this.value[nextEntry()]); }
/* 608:    */  }
/* 609:    */  
/* 610:610 */  public CharCollection values() { if (this.values == null) { this.values = new AbstractCharCollection() {
/* 611:    */        public CharIterator iterator() {
/* 612:612 */          return new Double2CharOpenHashMap.ValueIterator(Double2CharOpenHashMap.this);
/* 613:    */        }
/* 614:    */        
/* 615:615 */        public int size() { return Double2CharOpenHashMap.this.size; }
/* 616:    */        
/* 617:    */        public boolean contains(char v) {
/* 618:618 */          return Double2CharOpenHashMap.this.containsValue(v);
/* 619:    */        }
/* 620:    */        
/* 621:621 */        public void clear() { Double2CharOpenHashMap.this.clear(); }
/* 622:    */      };
/* 623:    */    }
/* 624:624 */    return this.values;
/* 625:    */  }
/* 626:    */  
/* 635:    */  @Deprecated
/* 636:    */  public boolean rehash()
/* 637:    */  {
/* 638:638 */    return true;
/* 639:    */  }
/* 640:    */  
/* 651:    */  public boolean trim()
/* 652:    */  {
/* 653:653 */    int l = HashCommon.arraySize(this.size, this.f);
/* 654:654 */    if (l >= this.n) return true;
/* 655:    */    try {
/* 656:656 */      rehash(l);
/* 657:    */    } catch (OutOfMemoryError cantDoIt) {
/* 658:658 */      return false; }
/* 659:659 */    return true;
/* 660:    */  }
/* 661:    */  
/* 678:    */  public boolean trim(int n)
/* 679:    */  {
/* 680:680 */    int l = HashCommon.nextPowerOfTwo((int)Math.ceil(n / this.f));
/* 681:681 */    if (this.n <= l) return true;
/* 682:    */    try {
/* 683:683 */      rehash(l);
/* 684:    */    } catch (OutOfMemoryError cantDoIt) {
/* 685:685 */      return false; }
/* 686:686 */    return true;
/* 687:    */  }
/* 688:    */  
/* 697:    */  protected void rehash(int newN)
/* 698:    */  {
/* 699:699 */    int i = 0;
/* 700:700 */    boolean[] used = this.used;
/* 701:    */    
/* 702:702 */    double[] key = this.key;
/* 703:703 */    char[] value = this.value;
/* 704:704 */    int newMask = newN - 1;
/* 705:705 */    double[] newKey = new double[newN];
/* 706:706 */    char[] newValue = new char[newN];
/* 707:707 */    boolean[] newUsed = new boolean[newN];
/* 708:708 */    for (int j = this.size; j-- != 0;) {
/* 709:709 */      while (used[i] == 0) i++;
/* 710:710 */      double k = key[i];
/* 711:711 */      int pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & newMask;
/* 712:712 */      while (newUsed[pos] != 0) pos = pos + 1 & newMask;
/* 713:713 */      newUsed[pos] = true;
/* 714:714 */      newKey[pos] = k;
/* 715:715 */      newValue[pos] = value[i];
/* 716:716 */      i++;
/* 717:    */    }
/* 718:718 */    this.n = newN;
/* 719:719 */    this.mask = newMask;
/* 720:720 */    this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 721:721 */    this.key = newKey;
/* 722:722 */    this.value = newValue;
/* 723:723 */    this.used = newUsed;
/* 724:    */  }
/* 725:    */  
/* 729:    */  public Double2CharOpenHashMap clone()
/* 730:    */  {
/* 731:    */    Double2CharOpenHashMap c;
/* 732:    */    
/* 734:    */    try
/* 735:    */    {
/* 736:736 */      c = (Double2CharOpenHashMap)super.clone();
/* 737:    */    }
/* 738:    */    catch (CloneNotSupportedException cantHappen) {
/* 739:739 */      throw new InternalError();
/* 740:    */    }
/* 741:741 */    c.keys = null;
/* 742:742 */    c.values = null;
/* 743:743 */    c.entries = null;
/* 744:744 */    c.key = ((double[])this.key.clone());
/* 745:745 */    c.value = ((char[])this.value.clone());
/* 746:746 */    c.used = ((boolean[])this.used.clone());
/* 747:747 */    return c;
/* 748:    */  }
/* 749:    */  
/* 757:    */  public int hashCode()
/* 758:    */  {
/* 759:759 */    int h = 0;
/* 760:760 */    int j = this.size;int i = 0; for (int t = 0; j-- != 0;) {
/* 761:761 */      while (this.used[i] == 0) i++;
/* 762:762 */      t = HashCommon.double2int(this.key[i]);
/* 763:763 */      t ^= this.value[i];
/* 764:764 */      h += t;
/* 765:765 */      i++;
/* 766:    */    }
/* 767:767 */    return h;
/* 768:    */  }
/* 769:    */  
/* 770:770 */  private void writeObject(ObjectOutputStream s) throws IOException { double[] key = this.key;
/* 771:771 */    char[] value = this.value;
/* 772:772 */    MapIterator i = new MapIterator(null);
/* 773:773 */    s.defaultWriteObject();
/* 774:774 */    for (int j = this.size; j-- != 0;) {
/* 775:775 */      int e = i.nextEntry();
/* 776:776 */      s.writeDouble(key[e]);
/* 777:777 */      s.writeChar(value[e]);
/* 778:    */    }
/* 779:    */  }
/* 780:    */  
/* 781:    */  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 782:782 */    s.defaultReadObject();
/* 783:783 */    this.n = HashCommon.arraySize(this.size, this.f);
/* 784:784 */    this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 785:785 */    this.mask = (this.n - 1);
/* 786:786 */    double[] key = this.key = new double[this.n];
/* 787:787 */    char[] value = this.value = new char[this.n];
/* 788:788 */    boolean[] used = this.used = new boolean[this.n];
/* 789:    */    
/* 791:791 */    int i = this.size; for (int pos = 0; i-- != 0;) {
/* 792:792 */      double k = s.readDouble();
/* 793:793 */      char v = s.readChar();
/* 794:794 */      pos = (int)HashCommon.murmurHash3(Double.doubleToRawLongBits(k)) & this.mask;
/* 795:795 */      while (used[pos] != 0) pos = pos + 1 & this.mask;
/* 796:796 */      used[pos] = true;
/* 797:797 */      key[pos] = k;
/* 798:798 */      value[pos] = v;
/* 799:    */    }
/* 800:    */  }
/* 801:    */  
/* 802:    */  private void checkTable() {}
/* 803:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.doubles.Double2CharOpenHashMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */