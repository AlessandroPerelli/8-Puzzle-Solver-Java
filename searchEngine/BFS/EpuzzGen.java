/**
 * State in an 8 puzzle problem
 *
 * search4 version for A*
 * 
 * Phil Green 2017
 * Heidi Christensen 2022 (heidi.christensen@sheffield.ac.uk)
*/

import java.util.Random;

public class EpuzzGen {
   int[][] tar = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
   Random gen;

   public EpuzzGen() {
      this.gen = new Random();
   }

   public EpuzzGen(int seed) {
      this.gen = new Random((long)seed);
   }

   public int[][] puzzGen(int diff) {
      int[][] puzz = new int[3][3];

      int i;
      int j;
      for(int n = 0; n <= 8; puzz[i][j] = n++) {
         i = this.gen.nextInt(3);

         for(j = this.gen.nextInt(3); puzz[i][j] != 0; j = this.gen.nextInt(3)) {
            i = this.gen.nextInt(3);
         }
      }

      if (this.impossible(puzz, diff)) {
         puzz = this.puzzGen(diff);
      }

      return puzz;
   }

   private boolean impossible(int[][] puzzq, int diff) {
      int[] flatp = new int[8];
      int f = 0;

      int i;
      int k;
      for(i = 0; i <= 2; ++i) {
         for(k = 0; k <= 2; ++k) {
            if (puzzq[i][k] > 0) {
               flatp[f] = puzzq[i][k];
               ++f;
            }
         }
      }

      i = 0;

      for(k = 0; k <= 6; ++k) {
         for(int l = k + 1; l <= 7; ++l) {
            if (flatp[l] < flatp[k]) {
               ++i;
            }
         }
      }

      k = this.manhattan(puzzq, this.tar);
      return i % 2 != 0 || k > diff;
   }

   private int manhattan(int[][] s, int[][] t) {
      int d = 0;
      int si = 0;
      int sj = 0;

      for(int n = 0; n <= 8; ++n) {
         int i;
         int j;
         for(i = 0; i <= 2; ++i) {
            for(j = 0; j <= 2; ++j) {
               if (s[i][j] == n) {
                  si = i;
                  sj = j;
               }
            }
         }

         for(i = 0; i <= 2; ++i) {
            for(j = 0; j <= 2; ++j) {
               if (t[i][j] == n) {
                  d = d + Math.abs(i - si) + Math.abs(j - sj);
               }
            }
         }
      }

      return d;
   }
}
