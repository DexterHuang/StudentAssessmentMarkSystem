/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement2;

import java.util.Collection;

/**
 *
 * @author Huang
 */
public class SortingHandler {

    Collection<SortableData> list;
    int biggest;

    public SortingHandler(Collection<SortableData> list) {
        this.list = list;
    }

    public void buildheap(SortableData[] l) {
        int count = list.size() - 1;
        for (int i = count / 2; i >= 0; i--) {
            maxheap(l, i);
        }
    }

    public void maxheap(SortableData[] l, int i) {
        int leftIndex = 2 * i;
        int rightIndex = 2 * i + 1;
        int count = list.size() - 1;
        if (leftIndex <= count && l[leftIndex].sortValue > l[i].sortValue) {
            biggest = leftIndex;
        } else {
            biggest = i;
        }

        if (rightIndex <= count && l[rightIndex].sortValue > l[biggest].sortValue) {
            biggest = rightIndex;
        }
        if (biggest != i) {
            swap(l, i, biggest);
            maxheap(l, biggest);
        }
    }

    public void swap(SortableData[] l, int i, int j) {
        SortableData t = l[i];
        l[i] = l[j];
        l[j] = t;
    }

    public SortableData[] sort() {
        SortableData[] l = list.toArray(new SortableData[list.size()]);
        buildheap(l);

        int count = list.size() - 1;
        for (int i = count; i > 0; i--) {
            swap(l, 0, i);
            count = count - 1;
            maxheap(l, 0);
        }
        return l;
    }
}
