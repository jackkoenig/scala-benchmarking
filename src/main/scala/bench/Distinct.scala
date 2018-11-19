
package bench

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Thread)
abstract class BenchmarkDistinct(n: Int) {

  var list: List[List[Int]] = _

  @Setup
  def init: Unit = {
    list = (0 until 10000).map(_ => (0 until n).toList).toList
  }

  @Benchmark
  def distinct: Unit = {
    for (l <- list) {
      l.distinct
    }
  }

  @Benchmark
  def optDistinct: Unit = {
    for (l <- list) {
			l.lengthCompare(3) match {
				case x if x < 0 =>
					val len = l.length
					if (len == 0 || len == 1 || l(0) != l(1)) l
					else l.tail
				case 0 =>
					val a = l(0)
					val b = l(1)
					val c = l(2)
					if (a == b) {
						if (a == c) l(c) else l.tail
					} else {
						if (a == c || b == c) List(a, b) else l
					}
				case _ => l.distinct
			}
    }
  }
}

class BenchmarkDistinct0 extends BenchmarkDistinct(0)
class BenchmarkDistinct1 extends BenchmarkDistinct(1)
class BenchmarkDistinct2 extends BenchmarkDistinct(2)
class BenchmarkDistinct3 extends BenchmarkDistinct(3)
class BenchmarkDistinct4 extends BenchmarkDistinct(4)
class BenchmarkDistinct8 extends BenchmarkDistinct(8)
class BenchmarkDistinct16 extends BenchmarkDistinct(16)
