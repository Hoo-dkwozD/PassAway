<script setup lang="ts">
import { useListStore } from "@/stores/list";

defineProps<{
    question: string
}>()
</script>

<template>
    <div class="to-do-wrapper">
        <div class="input-wrapper">
            <div class="input-card text-white">
                <h2>{{ question }}</h2>
                <div class="input-group">
                    <input v-model="entry" type="text" class="form-control" placeholder="Task name" aria-label="New task name" aria-describedby="save-entry-btn">
                    <button @click="saveEntry()" class="btn btn-success" type="button" id="save-entry-btn">Save</button>
                </div>
            </div>
        </div>
        <div class="list-display text-white">
            <ul class="list-wrapper">
                <li v-for="(item, idx) in list.items" class="list-group-item">
                    <input 
                        class="form-check-input" 
                        type="checkbox" 
                        @change="complete($event, idx)" 
                        v-model="localList" 
                        :value="item.id" 
                        :id="idx.toString()" 
                    />
                    &nbsp;
                    <label class="form-check-label" :for="idx.toString()">{{ item.name }}</label>
                </li>
            </ul>
        </div>
    </div>
</template>

<script lang="ts">
// Typings
interface JSONResponse {
    code: number
}

interface AddTaskJSONResponse extends JSONResponse {
    data: {
        id: number,
        name: string
    }
}

interface RemoveTaskJSONResponse extends JSONResponse {
    data: {}
}

export default {
    data() {
        const list = useListStore();
        const entry: string = "";
        const localList: any[] = [];

        return {
            list,
            entry,
            localList
        };
    },
    methods: {
        saveEntry() {
            if (this.entry !== null || this.entry === '') {
                const response: AddTaskJSONResponse = this.addTaskBackend(this.entry);

                if (response.code === 200) {
                    this.list.add(response.data.id, response.data.name);
                    this.entry = "";
                }
            }
        },
        addTaskBackend(name: string): AddTaskJSONResponse {
            return {
                code: 200,
                data: {
                    id: 123,
                    name: name
                }
            };
        },
        complete(e: Event, idx: number) {
            const response: RemoveTaskJSONResponse = this.removeTaskBackend(this.list.items[idx]);
            this.localList.pop();

            if (response.code === 202) {
                this.list.remove(idx);
            }
        },
        removeTaskBackend(item: { name: string, id: number }): RemoveTaskJSONResponse {
            return {
                code: 202,
                data: {}
            };
        }
    }
};
</script>

<style scoped>
.to-do-wrapper {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}

.input-wrapper, .list-display {
    padding: 0.5em;
}
</style>