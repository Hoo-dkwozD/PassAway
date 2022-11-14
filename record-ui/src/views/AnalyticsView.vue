<!-- As a administrator, I want to be able to view & download the statistics of the passes borrowed -->
<script lang="ts">
import * as d3 from "d3";
import { defineComponent} from "vue";
const userData: any[] = []
export default defineComponent({
    data() {
        return {
            xAxisVariable: "day",
            graphWidth: document.documentElement.clientWidth * 60 / 100,
            filteredData: [],
            userBorrowedPasses: [],
            calorieLimit: 0,
            numOfPasses: [
                {
                "Singapore Zoo": 1,
                "Sentosa Island": 2
                }
            ],
            userId:1,
            userData,
            currDate: new Date(),
        }
    },
    methods: {
        //takes in the userId and specified from and to date
        calculatePassesAndUpdate(userId, numOfPasses) {
            this.userId = userId
            this.numOfPasses = numOfPasses
            this.renderGraph(this.xAxisVariable)

        },
        getUserDetails(userId){
            const maxDate = new Date(Math.max(...Object.values(user.calorieDetails).map(element => {
                return new Date(element.date);
            })));
            let currDate = maxDate.getDate() + " " + maxDate.getMonth() + " " + maxDate.getFullYear();
            this.userData.push({
                date: currDate,
                numofPasses: this.numOfPasses
            })
            this.renderGraph(this.xAxisVariable)
        },
        //mouseover event handler function
        onMouseOver(d, i) {
            console.log("this is d", d)

            var svg = d3.select("#dashboard"),
                margin = 170,
                width = svg.attr("width") - margin,
                height = svg.attr("height") - margin
            var xScale = d3.scaleBand().range([0, width]).padding(0.4),
                yScale = d3.scaleLinear().range([height, 0]);

            let scale = 1
            let variable = this.xAxisVariable
            if (variable == "week") {
                scale = 7
            } else if (variable == "month") {
                scale = 30
            }
            xScale.domain(this.filteredData.map(function (d) { return d.date }));
            let mult = Math.pow(10, 1 - Math.floor(Math.log(this.calorieLimit * scale) / Math.LN10) - 1);
            let maxY = Math.ceil(this.calorieLimit * scale * mult) / mult
            yScale.domain([0, maxY]);

            d3.select(d.path[0]).attr('style', 'fill: orange');
            d3.select(d.path[0])
                .transition()     // adds animation
                .duration(400)
                .attr('width', xScale.bandwidth() + 10)
                .attr("x", (a) => xScale(a.date) - 5)
                .attr("y", function (d) { return yScale(d.calories) - 10; })
                .attr("height", function (d) { return height - yScale(d.calories) + 10; });


            let g = d3.select("g")
            g.append("text")
                .attr('class', 'val')
                .attr('x', function () {
                    return xScale(d.path[0]["__data__"].date) + ((xScale.bandwidth() + 10) / 4) - 5;
                })
                .attr('y', function () {
                    return yScale(d.path[0]["__data__"].calories) - 12;
                })
                .text(function () {
                    return [Number(d.path[0]["__data__"].calories).toFixed(2)];  // Value of the text
                });
        },
        onMouseOut(d, i) {
            // use the text label class to remove label on mouseout

            var svg = d3.select("#dashboard"),
                margin = 170,
                width = svg.attr("width") - margin,
                height = svg.attr("height") - margin
            var xScale = d3.scaleBand().range([0, width]).padding(0.4),
                yScale = d3.scaleLinear().range([height, 0]);

            let scale = 1
            let variable = this.xAxisVariable
            if (variable == "week") {
                scale = 7
            } else if (variable == "month") {
                scale = 30
            }
            xScale.domain(this.filteredData.map(function (d) { return d.date }));
            let mult = Math.pow(10, 1 - Math.floor(Math.log(this.calorieLimit * scale) / Math.LN10) - 1);
            let maxY = Math.ceil(this.calorieLimit * scale * mult) / mult
            yScale.domain([0, maxY]);

            d3.select(d.path[0]).attr('style', 'fill: steelblue');
            d3.select(d.path[0])
                .transition()     // adds animation
                .duration(400)
                .attr('width', xScale.bandwidth())
                .attr("x", function (d) { return xScale(d.date); })
                .attr("y", function (d) { return yScale(d.calories); })
                .attr("height", function (d) { return height - yScale(d.calories); });

            d3.selectAll('.val')
                .remove()
        },
        renderGraph(variable) {
            console.log('renderGraph() called')
            var svg = d3.select("#dashboard"),
                margin = 170,
                width = svg.attr("width") - margin,
                height = svg.attr("height") - margin

            svg.append("text")
                .attr("transform", "translate(100,0)")
                .attr("x", 50)
                .attr("y", 50)
                .attr("style", "font-size:24px;")
                .attr("id", "dashboardTitle")
                .text("Calorie Tracker");

            var xScale = d3.scaleBand().range([0, width]).padding(0.4),
                yScale = d3.scaleLinear().range([height, 0]);

            // DOM manipulation to remove <g> tag if it already exists
            if (document.getElementsByTagName("g").length >= 1) {

                document.getElementsByTagName("g")[0].remove()
                document.getElementById("dashboardTitle").remove()
            }

            var g = svg.append("g")
                .attr("transform", "translate(" + 100 + "," + 100 + ")");

            if (document.documentElement.clientWidth > 1200) {  // handling for higher viewports
                let days = {
                    0: "Sunday",
                    1: "Monday",
                    2: "Tuesday",
                    3: "Wednesday",
                    4: "Thursday",
                    5: "Friday",
                    6: "Saturday"
                }
                let months = {
                    0: "January",
                    1: "February",
                    2: "March",
                    3: "April",
                    4: "May",
                    5: "June",
                    6: "July",
                    7: "August",
                    8: "September",
                    9: "October",
                    10: "November",
                    11: "December"
                }
                let data = this.userData
                var filteredData = [{ date: "Sunday", calories: 0 }, { date: "Monday", calories: 0 }, { date: "Tuesday", calories: 0 }, { date: "Wednesday", calories: 0 }, { date: "Thursday", calories: 0 }, { date: "Friday", calories: 0 }, { date: "Saturday", calories: 0 }]
                if (variable == "day") {
                    for (let obj of data) {
                        for (let obj2 of filteredData) {
                            if (days[new Date(obj.date).getDay()] == obj2.date) {
                                obj2.calories += obj.calories
                            }
                        }
                    }
                } else if (variable == "week") {
                    filteredData = [{ date: "Week 1", calories: 0 }, { date: "Week 2", calories: 0 }, { date: "Week 3", calories: 0 }, { date: "Week 4", calories: 0 }, { date: "Week 5", calories: 0 }]
                    for (let obj of data) {
                        for (let obj2 of filteredData) {
                            if (Math.ceil((new Date(obj.date).getDate() + 1) / 7) == obj2.date.slice(-1)) {
                                obj2.calories += obj.calories
                            }
                        }
                    }
                } else {
                    filteredData = [{ date: "January", calories: 0 }, { date: "February", calories: 0 }, { date: "March", calories: 0 }, { date: "April", calories: 0 }, { date: "May", calories: 0 }, { date: "June", calories: 0 }, { date: "July", calories: 0 }, { date: "August", calories: 0 }, { date: "September", calories: 0 }, { date: "October", calories: 0 }, { date: "November", calories: 0 }, { date: "December", calories: 0 }]
                    for (let obj of data) {
                        for (let obj2 of filteredData) {
                            if (months[new Date(obj.date).getMonth()] == obj2.date) {
                                obj2.calories += obj.calories
                            }
                        }
                    }
                }
            } else {   // handling for smaller viewports
                let days = {
                    0: "Sun",
                    1: "Mon",
                    2: "Tue",
                    3: "Wed",
                    4: "Thu",
                    5: "Fri",
                    6: "Sat"
                }
                let months = {
                    0: "Jan",
                    1: "Feb",
                    2: "Mar",
                    3: "Apr",
                    4: "May",
                    5: "Jun",
                    6: "Jul",
                    7: "Aug",
                    8: "Sep",
                    9: "Oct",
                    10: "Nov",
                    11: "Dec"
                }
                let data = this.userData
                var filteredData = [{ date: "Sun", calories: 0 }, { date: "Mon", calories: 0 }, { date: "Tue", calories: 0 }, { date: "Wed", calories: 0 }, { date: "Thu", calories: 0 }, { date: "Fri", calories: 0 }, { date: "Sat", calories: 0 }]
                if (variable == "day") {
                    for (let obj of data) {
                        for (let obj2 of filteredData) {
                            if (days[new Date(obj.date).getDay()] == obj2.date) {
                                obj2.calories += obj.calories
                            }
                        }
                    }
                } else if (variable == "week") {
                    filteredData = [{ date: "Wk 1", calories: 0 }, { date: "Wk 2", calories: 0 }, { date: "Wk 3", calories: 0 }, { date: "Wk 4", calories: 0 }, { date: "Wk 5", calories: 0 }]
                    for (let obj of data) {
                        for (let obj2 of filteredData) {
                            if (Math.ceil((new Date(obj.date).getDate() + 1) / 7) == obj2.date.slice(-1)) {
                                obj2.calories += obj.calories
                            }
                        }
                    }
                } else {
                    filteredData = [{ date: "Jan", calories: 0 }, { date: "Feb", calories: 0 }, { date: "Mar", calories: 0 }, { date: "Apr", calories: 0 }, { date: "May", calories: 0 }, { date: "Jun", calories: 0 }, { date: "Jul", calories: 0 }, { date: "Aug", calories: 0 }, { date: "Sep", calories: 0 }, { date: "Oct", calories: 0 }, { date: "Nov", calories: 0 }, { date: "Dec", calories: 0 }]
                    for (let obj of data) {
                        for (let obj2 of filteredData) {
                            if (months[new Date(obj.date).getMonth()] == obj2.date) {
                                obj2.calories += obj.calories
                            }
                        }
                    }
                }
            }

            this.filteredData = filteredData

            let scale = 1
            if (variable == "week") {
                scale = 7
            } else if (variable == "month") {
                scale = 30
            }

            xScale.domain(filteredData.map(function (d) { return d.date }));
            let mult = Math.pow(10, 1 - Math.floor(Math.log(this.calorieLimit * scale) / Math.LN10) - 1);
            let maxY = Math.ceil(this.calorieLimit * scale * mult) / mult
            yScale.domain([0, maxY]);

            g.append("g")
                .attr("transform", "translate(0," + height + ")")
                .call(d3.axisBottom(xScale))
                .append("text")
                .attr("y", height - 250)
                .attr("x", width - 100)
                .attr("text-anchor", "end")
                .attr("stroke", "black")
                .text("Days");

            g.append("g")
                .call(d3.axisLeft(yScale)
                    .ticks(10))
                .append("text")
                .attr("transform", "rotate(-90)")
                .attr("y", 1)
                .attr("dy", "-3.1em")
                .attr("text-anchor", "middle")
                .attr("stroke", "black")
                .attr("style", "font-size: 18px; font-family: Avenir")
                .text("Calories Consumed");

            g.selectAll(".bar")
                .data(filteredData)
                .enter().append("rect")
                .attr("style", "fill: steelblue")
                .on("mouseover", this.onMouseOver) //Add listener for the mouseover event
                .on("mouseout", this.onMouseOut)   //Add listener for the mouseout event
                .attr("x", function (d) { return xScale(d.date); })
                .attr("y", function (d) { return yScale(d.calories); })
                .attr("width", xScale.bandwidth())
                .transition()
                .ease(d3.easeLinear)
                .duration(300)
                .delay(function (d, i) {
                    return i * 50;
                })
                .attr("height", function (d) { return height - yScale(d.calories); });
        }
    }
})
</script>

<style scoped>
.box {
    background-color: white;
    border-radius: 10px;
}
</style>

<template>
    <div class="row box">
        <div style="width:90%" class="p-3 mx-auto col-6">
            <h2 style="text-align:left">Overview</h2>
            <div style="border:1px solid black;" class="mb-3">
                <svg :width="graphWidth" height="600" id="dashboard"></svg>

                <div class="row d-flex justify-content-end m-4">
                    <label for="colFormLabelSm" class="col-1 col-form-label col-form-label-sm"
                        style="text-align: end">Select</label>
                    <div class="dropdown col-2">
                        <img src="../assets/calendar-svgrepo-com.svg"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

