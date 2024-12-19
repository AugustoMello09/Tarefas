module.exports = {
  content: ["./src/**/*.{html,ts}", "./node_modules/flowbite/**/*.js"],
  theme: {
    extend: {
      fontFamily: {
        'dm': ['DM Sans', 'sans-serif'],
        'Gro' : ['Space Grotesk', 'sans-serif']
      },
      screens: {
        'auto1': {'max' : '1280px'},
        'auto': {'max' : '1024px'},
        'menor': {'max' : '824px'},
        'xr': {'max' : '599px'},
        'xd': { 'max': '409px' },
        'xdd': { 'max': '320px' }, 
      }
    },
  },
  plugins: [
    require('flowbite/plugin')
  ],
}

