const prompt = require('prompt-sync')()
let letra
let count = 1

letra = prompt('digite letras: ')


for(let i = 0; i <= letra.length -1; i++){
    if(letra[i] == letra[i +1]){
        count ++
    }else{
        console.log(count + letra[i])
        count = 1
    }
}