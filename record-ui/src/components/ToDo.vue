<template>
    <div class="to-do-wrapper">
        <div class="input-wrapper">
            <div class="input-card text-white">
                <h2>{{ question }}</h2>
                <div class="input-group">
                    <input v-model="entry" @keyup.enter="saveEntry()" type="text" class="form-control" placeholder="Task name" aria-label="New task name" aria-describedby="save-entry-btn">
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
import { defineComponent } from 'vue';
import { useListStore } from "@/stores/list";
import axios from 'axios';

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
    message: string
}

export default defineComponent({
    data() {
        const list = useListStore();
        const entry: string = "";
        const localList: any[] = [];

        return {
            list: list,
            entry: entry,
            localList: localList
        };
    },
    props: {
        question: String
    },
    async created() {
        try {
            this.list.removeAll();

            const res = await axios.get(import.meta.env.VITE_API_URL + "api/task");

            const data = res.data;

            if (data.code === 200) {
                for (let task of data.data) {
                    this.list.add(task.id, task.name);
                }
            }
        } catch (err) {
            console.error(err);
        }
    },
    methods: {
        async saveEntry() {
            if (this.entry !== null || this.entry === '') {
                const response: JSONResponse | AddTaskJSONResponse | any = await this.addTaskBackend(this.entry);

                if (response.code === 201 && this.isAddTaskJSONResponse(response)) {
                    this.list.add(response.data.id, response.data.name);
                    this.entry = "";
                }
            }
        },
        async addTaskBackend(name: string): Promise<JSONResponse | AddTaskJSONResponse | any> {
            try {
                const res = await axios.post(
                    import.meta.env.VITE_API_URL + "api/task",
                    {
                        name: name
                    }
                );

                return res.data;
            } catch (err) {
                return {
                    code: 500
                };
            }
        },
        isAddTaskJSONResponse(obj: any): obj is AddTaskJSONResponse {
            return "data" in obj;
        },
        isRemoveTaskJSONResponse(obj: any): obj is RemoveTaskJSONResponse {
            return "message" in obj;
        },
        async complete(e: Event, idx: number) {
            const response: JSONResponse | RemoveTaskJSONResponse | any = await this.removeTaskBackend(this.list.items[idx]);
            this.localList.pop();

            if (response.code === 200 && this.isRemoveTaskJSONResponse(response)) {
                this.list.remove(idx);
            }
        },
        async removeTaskBackend(item: { name: string, id: number }): Promise<JSONResponse | RemoveTaskJSONResponse | any> {
            try {
                const res = await axios.delete(
                    import.meta.env.VITE_API_URL + `api/task/${item.id}`,
                    {
                        data: item
                    }
                );
                return res.data;
            } catch (err) {
                return {
                    code: 500
                };
            }
        }
    }
});
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