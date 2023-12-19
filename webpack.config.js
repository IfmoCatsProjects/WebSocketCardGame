const path = require("path");
module.exports = {
    watch: true,
    devtool: "source-map",
    entry: {
        distribution: [
            './src/main/resources/static/js/game/distribution.js'
        ],
        cardManager: [
            './src/main/resources/static/js/game/cardManager.js'
        ],
        events: [
            './src/main/resources/static/js/game/events.js'
        ]
    },
    output: {
        path: path.resolve(__dirname, 'src/main/resources/static/js/dist'),
        filename: "[name].js"
    },
    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/,
            use: {
                loader: "babel-loader",
            },
        },
            {
                test: /\.css$/,
                exclude: /node_modules/,
                use: ["style-loader", "css-loader"]
            }],
    },
}