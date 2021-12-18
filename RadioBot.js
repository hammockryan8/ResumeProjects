const {
    Client,
    Attachment
} = require('discord.js');
const bot = new Client();

const ytdl = require("ytdl-core");

const token = //this token has been redacted for privacy purposes;

const PREFIX = '!';

var version = '1.0';

var servers = {};

bot.on('ready', () =>{
    console.log('This bot is alive! ' + version);
})

bot.on('message', message =>{
    let args = message.content.substring(PREFIX.length).split(" ");
    switch(args[0]){
        case 'play':
            var music = 'https://www.youtube.com/watch?v=lbDQhpRTx0M'
            var FMA = 'FMA';
            function play(connection, message){
                var server = servers[message.guild.id];

                server.dispatcher = connection.play(ytdl(server.queue[0], {filter: "audioonly"}));

                server.queue.shift();

                server.dispatcher.on("end", function(){
                    if(server.queue[0]){
                        play(connection, message);
                    }else{
                        connection.disconnect();
                    }
                });
            }

            if(args[1] !== FMA){
                message.channel.send("choosing other radio bot");
                return;
            }

            if(!message.member.voice.channel){
                message.channel.send("hop on voice channel");
                return;
            }

            if(!servers[message.guild.id]) servers[message.guild.id] = {
                queue: []
            }
            var server = servers[message.guild.id];

            server.queue.push(music);

            if(!message.member.voice.connection) message.member.voice.channel.join().then(function(connection){
                play(connection, message);
            })
        break;
    }
})

bot.login(token);