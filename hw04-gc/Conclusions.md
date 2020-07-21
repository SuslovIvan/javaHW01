## Задача:
Сравнить различные GC и выбрать наилучший в рамках данного приложения.

## Реализация:
Приложение с периодичностью добавляет в коллекцию 3000 элементов, после чего удаляет половину добавленного и засыпает на 0.1 секунды. Если ограничение по времени не установлено, то приложение работает до возникновения исключения OutOfMemoryError, либо до переполнения ArrayList'a.

## Статистика:
Параметры среды:
* Java version "13.0.2" 2020-01-14
* Java(TM) SE Runtime Environment (build 13.0.2+8)
* Java HotSpot(TM) 64-Bit Server VM (build 13.0.2+8, mixed mode, sharing)
* ОС: Windows 10 Домашняя
* CP: Intel® Core™ i5-8600 (9 MB/4,30 GHz)
* RAM: 16,0 GB (DDR4/2400 MGz)


Параметры запуска:
* -Xms256m -Xmx256m -XX:+UseG1GC
* -Xms256m -Xmx256m -XX:+UseSerialGC
* -Xms256m -Xmx256m -XX:+UseParallelGC

| GC | GC info |GC time | application time | application time without stop-the-world | GC time / app time  |
|:---:|:---:|:---:|:---:|:---:|:---:|    
| SerialGC | Copy/49coll/0,891s     MarkSweepCompact/94coll/15,507s | *16,398s* | **5m 21s** | **5m 4s** | 0,0510% |  
| ParallelGC | PSMarkSweep/89coll/13,564s     PSScavenge/60coll/0,698s | 14,262s | *4m 36s* | *4m 21s* | *0,0516%* |   
| G1GC | G1YoungGeneration/221coll/1,185s     G1OldGeneration/74coll/6,033s | **7,218s** | 5m 8s | 5m 1s | **0,0234%** | 


Параметры запуска:
* -Xms64m -Xmx64m -XX:+UseG1GC
* -Xms64m -Xmx64m -XX:+UseSerialGC
* -Xms64m -Xmx64m -XX:+UseParallelGC

| GC | GC info |GC time | application time | application time without stop-the-world | GC time / app time  |
|:---:|:---:|:---:|:---:|:---:|:---:|    
| SerialGC | Copy/49coll/0,312s     MarkSweepCompact/61coll/2,893s | 3,205s | **53,2s** | **50,0s** | 0,060% |  
| ParallelGC | PSMarkSweep/75coll/3,376s     PSScavenge/58coll/0,210s | *3,586s* | 51,9s | 48,4s | *0,069%* |   
| G1GC | G1YoungGeneration/189coll/0,456s     G1OldGeneration/5coll/0,175s | **0,631s** | *47,5s* | *46,9s* | **0,013%** | 


## Вывод:
В рамках данного приложения были выявлены следующие показатели:

* Наибольшее время жизни приложения обеспечивает `SerialGC` вне зависимости от выделенной оперативной памяти. При этом `SerialGC` имеет наибольший суммарный рост stop-the-world времени и наименьшее количестов сборок.
* Самым выгодным по соотношению времени работы GC от общего времени выполнения приложения является - `G1GC`, производя частые, но незначительные по времени сборки.
* Хуже всего показал себя `ParallelGC`, обеспечивая небольшой жизненый срок приложения и значительно длинные сборки мусора. В обоих случаях `ParallelGC` имеет наибольшее отношение времени, потраченное на сборку мусора, относительно непродолжительного времени жизни.
