import React from 'react';
import logo from './logo.svg';
import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/layout/Header';
import 'bootstrap/dist/css/bootstrap.min.css'
import {BrowserRouter as Router, Route} from 'react-router-dom';
import AddProject from './components/projects/AddProject';
import UpdateProject from './components/projects/UpdateProject';
import {Provider} from 'react-redux';
import store from './store';

function App() {
  return (
    <Provider store={store}>
    <Router>
    <div className="container">
      <Header/>
    </div>
    <Route path="/dashboard" component={Dashboard}></Route>
    <Route path="/addProject" component={AddProject}></Route>
    <Route path="/updateProject/:id" component={UpdateProject}></Route>
    </Router>
    </Provider>
  );
}

export default App;
