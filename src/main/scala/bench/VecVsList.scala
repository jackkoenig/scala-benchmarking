
package bench

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Thread)
class VecVsListBenchmark {

  var vector: Vector[Int] = _
  var list: List[Int] = _

  @Setup
  def init: Unit = {
    vector = (0 until 10000).toVector
    list = (0 until 10000).toList
  }

  @Benchmark
  def listAppend: Unit = seqAppend(10000, Nil)

  @Benchmark
  def vectorAppend: Unit = seqAppend(10000, Vector())

  @Benchmark
  def listPrepend: Unit = seqPrepend(10000, Nil)

  @Benchmark
  def vectorPrepend: Unit = seqPrepend(10000, Vector())

  @Benchmark
  def listMap: Unit = list.map(_ + 1)

  @Benchmark
  def vectorMap: Unit = vector.map(_ + 1)

  def seqAppend(size: Int, empty: Seq[Int]): Seq[Int] = {
    var i = 0
    var xs = empty
    while (i < size) {
      xs = xs :+ i
      i += 1
    }
    xs
  }

  def seqPrepend(size: Int, empty: Seq[Int]): Seq[Int] = {
    var i = 0
    var xs = empty
    while (i < size) {
      xs = i +: xs
      i += 1
    }
    xs
  }
}
