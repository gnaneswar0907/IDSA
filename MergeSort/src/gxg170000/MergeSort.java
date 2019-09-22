package gxg170000;

public class MergeSort {

    private final static int T = 100;

    public void mergeSortTake1(int[] A){
        mergeSortTake1(A, 0, A.length-1);
    }

    private void mergeSortTake1(int[] A, int low, int high){
        if(low<high){
            int mid = (low + high)/2;
            mergeSortTake1(A, low, mid);
            mergeSortTake1(A, mid+1, high);
            mergeTake1(A, low, mid, high);
        }
    }

    private void mergeTake1(int[] A, int low, int mid, int high){
        int n1 = mid - low + 1;
        int n2 = high - mid;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for(int i=0;i<L.length;i++){
            L[i] = A[i+low];
        }

        for(int i=0;i<R.length;i++){
            R[i] = A[i+mid+1];
        }

        int i = 0, j = 0, k=low;

        while (i<n1 && j<n2){
            if(L[i]<=R[j]){
                A[k++] = L[i++];
            }
            else {
                A[k++] = R[j++];
            }
        }
        while (i<n1){
            A[k++] = L[i++];
        }
        while (j<n2){
            A[k++] = R[j++];
        }
    }

    public void mergeSortTake3(int[] A){
        int[] B = A.clone();
        mergeSortTake3(A, B, 0, A.length-1);
    }

    private void mergeSortTake3(int[] A, int[] B, int low, int high){
        if(low<high){
            int mid = (low + high)/2;
            mergeSortTake3(B, A, low, mid);
            mergeSortTake3(B, A, mid+1, high);
            mergeTake3(A, B, low, mid, high);
        }
    }

    private void mergeTake3(int[] A, int[] B, int low, int mid, int high){
        int i = low, j = mid+1, k=low;
        while (i<=mid && j<=high){
            if(B[i]<=B[j]){
                A[k++] = B[i++];
            }
            else {
                A[k++] = B[j++];
            }
        }
        while (i<=mid) A[k++] = B[i++];
        while (j<=high) A[k++] = B[j++];
    }

    public void mergeSortTake4(int[] A){
        int[] B = A.clone();
        mergeSortTake4(A, B, 0, A.length-1);
    }

    private void mergeSortTake4(int[] A, int[] B, int low, int high){
        if(high-low<T){
            insertionSort(A,low,high);
        }
        else{
            mergeSortTake3(A, B, low, high);
        }
    }

    private void insertionSort(int[] A, int low, int high){
        for(int i=1;i<A.length;i++){
            for(int j=i;j>0;j--){
                if(A[j]<=A[j-1]){
                    int temp = A[j];
                    A[j] = A[j-1];
                    A[j-1] = temp;
                }
            }
        }
    }

    public void mergeSortTake5(int[] A){
        int[] B = A.clone();
        mergeSortTake5(A, B);
    }

    private void mergeSortTake5(int[] A, int[] B){
        int [] T = A;
        for(int i=1;i<A.length;i=2*i){
            for(int j=0;j<A.length-1;j+=2*i){
               int mid = Math.min(j+i-1, A.length-1);
               int right = Math.min(j+2*i-1, A.length-1);
               mergeTake3(A, B, j, mid, right);
            }
            T = A;
            A = B;
            B = T;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{ 5,1,9,4,2,0,20,12,44,3,6, 100, 99,98 };
        MergeSort sorting = new MergeSort();
        sorting.mergeSortTake5(nums);
        for(int i : nums){
            System.out.print(i + "  ");
        }
    }
}
