import { defineStore } from 'pinia';

// Typings
interface ToDoTask {
    name: string,
    id: number
}

export interface StoreState {
    items: ToDoTask[]
}

export const useListStore = defineStore({
    id: 'list',
    state: (): StoreState => ({
        items: []
    }),
    actions: {
        add(id: number, name: string): void {
            this.items.push({
                id: id,
                name: name
            });
        },
        remove(idx: number): void {
            this.items.splice(idx, 1);
            console.log(this.items);
        }
    }
});